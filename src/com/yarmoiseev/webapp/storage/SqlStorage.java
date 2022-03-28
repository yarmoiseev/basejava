package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.exception.NotExistStorageException;
import com.yarmoiseev.webapp.model.*;
import com.yarmoiseev.webapp.sql.SqlHelper;
import com.yarmoiseev.webapp.util.JsonParser;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) throws SQLException {
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.queryExecute("DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() != 1) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContacts(conn, r);
            insertContact(conn, r);
            return null;
        });

    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            insertContact(conn, r);
            insertSection(conn, r);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.queryExecute("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        " LEFT JOIN section s " +
                        "        ON r.uuid = s.resume_uuid" +
                        "     WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(rs, r);
                        addSection(rs, r);
                    } while (rs.next());

                    return r;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.queryExecute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {

        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();

            try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    String uuid = rs.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, rs.getString("full_name")));
                }
            }

            try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    Resume resume = resumes.get(rs.getString("resume_uuid"));
                    addContact(rs, resume);
                }
            }
            return new ArrayList<>(resumes.values());
         });
    }

    @Override
    public int size() {
        return sqlHelper.queryExecute("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Connection conn, Resume r) {
        sqlHelper.queryExecute("DELETE  FROM contact WHERE resume_uuid=?", ps -> {
            ps.setString(1, r.getUuid());
            ps.execute();
            return null;
        });
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            r.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void insertSection(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, section_type, section_content) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().toString());
                ps.setString(3, e.getValue().toString());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        String content  = rs.getString("section_content");
        if (content != null) {
            SectionType sectionType = SectionType.valueOf(rs.getString("section_type"));
            if(sectionType == SectionType.PERSONAL | sectionType == SectionType.OBJECTIVE) {
                r.addSection(sectionType, new TextSection(content));
            } else {
                List<String> items = new ArrayList<>(Arrays.asList(content.split("\\n")));
                BulletTextSection bulletTextSection = new BulletTextSection(items);
                r.addSection(sectionType, bulletTextSection);
            }
        }
    }

}
