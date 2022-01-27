package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.storage.serializestrategy.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamSerializer()));
    }
}