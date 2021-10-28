package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void saveToStorage(Resume r, int index) {
        if (index < 0) {
            if (storageSize != storage.length) {
                storage[storageSize] = r;
                storageSize++;
            } else System.out.println("The storage is full. Can't add new resume");
        } else System.out.println("These resume with uuid: \"" + r.getUuid() + "\" already exist");
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
