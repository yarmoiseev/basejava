package com.yarmoiseev.webapp.util;

import com.yarmoiseev.webapp.exception.ExistStorageException;
import com.yarmoiseev.webapp.exception.StorageException;
import com.yarmoiseev.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory con) {
        this.connectionFactory = con;
    }

    public <T> T queryExecute(String sql, Executor<T> uniqueCode) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return uniqueCode.runExecutor(ps);
        } catch (SQLException e) {
            String errorCode = e.getSQLState();
            if (errorCode.equals("23505")) {
                throw new ExistStorageException(errorCode);
            } else throw new StorageException(e);
        }
    }

    public interface Executor<T> {
        T runExecutor(PreparedStatement ps) throws SQLException;
    }
}
