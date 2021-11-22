package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.exception.ExistStorageException;
import com.yarmoiseev.webapp.exception.NotExistStorageException;
import com.yarmoiseev.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            setByIndex(index, r);
        } else throw new NotExistStorageException(r.getUuid());
    }

    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        if (index < 0) {
            addToStorage(r);
        } else throw new ExistStorageException(r.getUuid());
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getByIndex(index, uuid);
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            removeByIndex(index, uuid);
        } else throw new NotExistStorageException(uuid);
    }

    protected abstract int findIndex(String uuid);

    protected abstract void setByIndex(int index, Resume r);

    protected abstract void addToStorage(Resume r);

    protected abstract Resume getByIndex(int index, String uuid);

    protected abstract void removeByIndex(int index, String uuid);
}
