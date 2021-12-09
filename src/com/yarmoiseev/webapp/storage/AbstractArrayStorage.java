package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.exception.StorageException;
import com.yarmoiseev.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public List<Resume> getAllSorted() {
        ArrayList<Resume> resumeArrayList = new ArrayList<>(Arrays.asList(Arrays.copyOf(storage, storageSize)));
        Collections.sort(resumeArrayList);
        return resumeArrayList;
    }

    public int size() {
        return storageSize;
    }

    @Override
    protected void saveToStorage(Resume r, Object index) {
        if (storageSize != STORAGE_LIMIT) {
            insertElement(r, (Integer) index);
            storageSize++;
        } else throw new StorageException("Storage overflow ", r.getUuid());
    }

    @Override
    protected void updateInStorage(Resume r, Object index) {
        storage[(Integer) index] = r;
    }

    @Override
    protected Resume getFromStorage(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected void removeFromStorage(Object index) {
        System.arraycopy(storage, (Integer) index + 1, storage, (Integer) index,
                storageSize - 1 - (Integer) index);
        storageSize--;
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    protected abstract void insertElement(Resume r, int index);

    protected abstract Integer getSearchKey(String uuid);
}
