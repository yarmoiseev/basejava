package com.yarmoiseev.webapp.util;

import com.yarmoiseev.webapp.exception.ExistStorageException;
import com.yarmoiseev.webapp.exception.StorageException;
import com.yarmoiseev.webapp.model.Resume;
import com.yarmoiseev.webapp.sql.ConnectionFactory;
import com.yarmoiseev.webapp.storage.SqlStorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public Resume connectionExecute(SqlStorage.Executor uniqueCode, String sql) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            Resume r = uniqueCode.runExecutor(ps);
            return r;
        } catch (SQLException e) {
            String errorCode = e.getSQLState();
            if (errorCode.equals("23505")) {
                throw new ExistStorageException(errorCode);
            } else throw new StorageException(e);
        }
    }

}
