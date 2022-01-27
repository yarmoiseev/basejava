package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.storage.serializestrategy.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
    }
}