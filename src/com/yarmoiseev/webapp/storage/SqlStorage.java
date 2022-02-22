package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.exception.NotExistStorageException;
import com.yarmoiseev.webapp.exception.StorageException;
import com.yarmoiseev.webapp.model.Resume;
import com.yarmoiseev.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;


    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume")) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void update(Resume r) {
        connectionExecute(new UniqueCode() {
            @Override
            public void doUnique(PreparedStatement ps) throws SQLException {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                ps.executeUpdate();
            }
        }, "UPDATE resume SET full_name = ? WHERE uuid = ?");


        //////
/*        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/
    }

    @Override
    public void save(Resume r) {
        connectionExecute(new UniqueCode() {
            @Override
            public void doUnique(PreparedStatement ps) throws SQLException {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
        }, "INSERT INTO resume (uuid, full_name) VALUES (?,?)");

        //////
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
//            ps.setString(1, r.getUuid());
//            ps.setString(2, r.getFullName());
//            ps.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public Resume get(String uuid) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid =?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
            ps.setString(1, uuid);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException(e);
        }

    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = new ArrayList<>();
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume")) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid").trim();
                Resume resume = new Resume(uuid, rs.getString("full_name"));
                resumeList.add(resume);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        Collections.sort(resumeList);
        return resumeList;
    }

    @Override
    public int size() {
        int size = 0;
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT count(*) FROM resume")) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                size = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return size;
    }

    public interface UniqueCode {
        void doUnique(PreparedStatement ps) throws SQLException;
    }

    private void connectionExecute(UniqueCode uniqueCode, String sql) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            uniqueCode.doUnique(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
