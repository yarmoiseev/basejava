package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.exception.StorageException;
import com.yarmoiseev.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() throws Exception {
        int j = storage.size();
        int storageLimit = AbstractArrayStorage.STORAGE_LIMIT;
        for (int i = 0; i < storageLimit - j; i++) {
            try {
                storage.save(new Resume("dummy" + i, "full name"));
            } catch (StorageException se) {
                Assert.fail("Early storage overflow");
            }
        }
        storage.save(new Resume("uuid4", "full name"));
    }

}