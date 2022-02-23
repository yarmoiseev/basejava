package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.Config;
import com.yarmoiseev.webapp.util.SqlHelper;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(new SqlHelper(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPass())));
    }
}