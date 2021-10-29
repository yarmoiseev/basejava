package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void saveToStorage(Resume r, int index) {
        storage[storageSize] = r;
    }

    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
