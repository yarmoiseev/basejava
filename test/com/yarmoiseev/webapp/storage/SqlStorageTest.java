package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.Config;
import com.yarmoiseev.webapp.util.SqlHelper;

import java.sql.SQLException;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() throws SQLException {
        super(Config.get().getSqlStorage());
    }
}