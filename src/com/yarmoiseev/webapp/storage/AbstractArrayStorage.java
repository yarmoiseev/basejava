package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.exception.StorageException;
import com.yarmoiseev.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);
    }

    public int size() {
        return storageSize;
    }

    public abstract void saveToStorage(Resume r, int index);

    @Override
    protected void setByIndex(int index, Resume r) {
        storage[index] = r;
    }

    @Override
    protected void addToStorage(Resume r) {
        int index = findIndex(r.getUuid());
        if (storageSize != storage.length) {
            saveToStorage(r, index);
            storageSize++;
        } else throw new StorageException("Storage overflow ", r.getUuid());
    }

    @Override
    protected Resume getByIndex(int index, String uuid) {
        return storage[index];
    }

    @Override
    protected void removeByIndex(int index, String uuid) {
        System.arraycopy(storage, index + 1, storage, index, storageSize - 1 - index);
        storageSize--;
    }

}
