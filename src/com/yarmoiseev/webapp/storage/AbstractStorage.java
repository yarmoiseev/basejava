package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.exception.ExistStorageException;
import com.yarmoiseev.webapp.exception.NotExistStorageException;
import com.yarmoiseev.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    public void update(Resume r) {
        SK searchKey = getExistingSearchKey(r.getUuid());
        updateInStorage(r, searchKey);
    }

    public void save(Resume r) {
        SK searchKey = getNotExistingSearchKey(r.getUuid());
        saveToStorage(r, searchKey);
    }

    public Resume get(String uuid) {
        SK searchKey = getExistingSearchKey(uuid);
        return getFromStorage(searchKey);
    }

    public void delete(String uuid) {
        SK searchKey = getExistingSearchKey(uuid);
        removeFromStorage(searchKey);
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    public List<Resume> getAllSorted() {
        ArrayList<Resume> resumeArrayList = new ArrayList<>(getListFromStorage());
        Collections.sort(resumeArrayList);
        return resumeArrayList;
    }

    protected abstract void saveToStorage(Resume r, SK searchKey);

    protected abstract void updateInStorage(Resume r, SK searchKey);

    protected abstract Resume getFromStorage(SK searchKey);

    protected abstract void removeFromStorage(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract List<Resume> getListFromStorage();


}
