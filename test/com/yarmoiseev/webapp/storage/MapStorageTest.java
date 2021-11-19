package com.yarmoiseev.webapp.storage;

import org.junit.Ignore;
import org.junit.Test;

public class MapStorageTest extends AbstractArrayStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Ignore
    @Override
    public void update() throws Exception {
    }

    @Ignore
    @Override
    public void get() throws Exception {
    }

    @Ignore
    @Override
    public void save() throws Exception {
    }

    @Ignore
    @Test
    public void saveStorageOverflow() {
    }
}