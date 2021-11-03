package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.exception.ExistStorageException;
import com.yarmoiseev.webapp.exception.NotExistStorageException;
import com.yarmoiseev.webapp.exception.StorageException;
import com.yarmoiseev.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else throw new NotExistStorageException(r.getUuid());
    }

    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (index < 0) {
            if (storageSize != storage.length) {
                saveToStorage(r, index);
                storageSize++;
            } else throw new StorageException("Storage overflow ", r.getUuid());
        } else throw new ExistStorageException(r.getUuid());
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, storageSize - 1 - index);
            storageSize--;
        } else throw new NotExistStorageException(uuid);
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

    protected abstract int findIndex(String uuid);

    public abstract void saveToStorage(Resume r, int index);

}
