package com.yarmoiseev.webapp.sql;

import com.yarmoiseev.webapp.exception.ExistStorageException;
import com.yarmoiseev.webapp.exception.StorageException;
import com.yarmoiseev.webapp.sql.ConnectionFactory;
import com.yarmoiseev.webapp.util.ExceptionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory con) {
        this.connectionFactory = con;
    }

    public void queryExecute(String sql) {
        queryExecute(sql, PreparedStatement::execute);
    }

    public <T> T queryExecute(String sql, Executor<T> uniqueCode) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return uniqueCode.runExecutor(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }
    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface Executor<T> {
        T runExecutor(PreparedStatement ps) throws SQLException;
    }
}
