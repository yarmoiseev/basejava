package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;

    public int size() {
        return storageSize;
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.println("Can't find. Resume with uuid: \"" + uuid + "\" doesn't exist");
        return null;
    }

    protected abstract int findIndex(String uuid);
}
