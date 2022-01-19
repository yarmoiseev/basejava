package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.storage.serializestrategy.ObjectStreamSerializer;

import static org.junit.Assert.*;

public class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}