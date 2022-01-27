package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.storage.serializestrategy.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamSerializer()));
    }
}