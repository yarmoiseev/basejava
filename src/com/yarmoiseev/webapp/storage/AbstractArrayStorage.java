package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.exception.StorageException;
import com.yarmoiseev.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class  AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    public int size() {
        return storageSize;
    }

    @Override
    protected void saveToStorage(Resume r, Integer index) {
        if (storageSize != STORAGE_LIMIT) {
            insertElement(r, index);
            storageSize++;
        } else throw new StorageException("Storage overflow ", r.getUuid());
    }

    @Override
    protected void updateInStorage(Resume r, Integer index) {
        storage[index] = r;
    }

    @Override
    protected Resume getFromStorage(Integer index) {
        return storage[index];
    }

    @Override
    protected void removeFromStorage(Integer index) {
        System.arraycopy(storage, index + 1, storage, index,
                storageSize - 1 - index);
        storageSize--;
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    protected abstract void insertElement(Resume r, int index);

    protected abstract Integer getSearchKey(String uuid);

    @Override
    protected List<Resume> getListFromStorage() {
        return Arrays.asList(Arrays.copyOf(storage, storageSize));
    }
}
