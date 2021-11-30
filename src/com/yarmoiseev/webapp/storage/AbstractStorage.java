package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.exception.ExistStorageException;
import com.yarmoiseev.webapp.exception.NotExistStorageException;
import com.yarmoiseev.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        Object searchKey = getExistingSearchKey(r.getUuid());
        updateInStorage(r, searchKey);
    }

    public void save(Resume r) {
        Object searchKey = getNotExistingSearchKey(r.getUuid());
        saveToStorage(r, searchKey);
    }

    public Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return getFromStorage(searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        removeFromStorage(searchKey);
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract void saveToStorage(Resume r, Object searchKey);

    protected abstract void updateInStorage(Resume r, Object searchKey);

    protected abstract Resume getFromStorage(Object searchKey);

    protected abstract void removeFromStorage(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);


}
