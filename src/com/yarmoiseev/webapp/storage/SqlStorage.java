package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.exception.NotExistStorageException;
import com.yarmoiseev.webapp.model.Resume;
import com.yarmoiseev.webapp.util.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlStorage implements Storage {
    private SqlHelper sqlHelper;

    public SqlStorage(SqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    @Override
    public void clear() {
        sqlHelper.connectionExecute(ps -> {
            ps.execute();
            return null;
        }, "DELETE FROM resume");
    }

    @Override
    public void update(Resume r) { //TODO notExist
        sqlHelper.connectionExecute(ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        }, "UPDATE resume SET full_name = ? WHERE uuid = ?");
    }

    @Override
    public void save(Resume r) {
        sqlHelper.connectionExecute(ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.executeUpdate();
            return null;
        }, "INSERT INTO resume (uuid, full_name) VALUES (?,?)");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.connectionExecute(ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        }, "SELECT * FROM resume r WHERE r.uuid =?");
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.connectionExecute(ps -> {
            ps.setString(1, uuid);
            int count = ps.executeUpdate();
            if (count == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        }, "DELETE FROM resume WHERE uuid = ?");
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumeList = new ArrayList<>();

        sqlHelper.connectionExecute(ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid").trim();
                Resume resume = new Resume(uuid, rs.getString("full_name"));
                resumeList.add(resume);
            }
            return null;
        }, "SELECT * FROM resume");
        Collections.sort(resumeList);
        return resumeList;
    }

    @Override
    public int size() {
        final int[] size = {0};
        sqlHelper.connectionExecute(ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                size[0] = rs.getInt(1);
            }
            return null;
        }, "SELECT count(*) FROM resume");
        return size[0];
    }

    public interface Executor {
        Resume runExecutor(PreparedStatement ps) throws SQLException;
    }


}
