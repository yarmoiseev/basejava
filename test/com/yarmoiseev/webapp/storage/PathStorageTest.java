package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.storage.serializestrategy.ObjectStreamSerializer;

import static org.junit.Assert.*;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializer()));
    }
}