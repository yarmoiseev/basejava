package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest{
    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPass()));
    }
}