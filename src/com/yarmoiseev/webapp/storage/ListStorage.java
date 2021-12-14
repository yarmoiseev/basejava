package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * ArrayList based storage for Resumes
 */

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void saveToStorage(Resume r, Integer searchKey) {
        storage.add(r);
    }

    @Override
    protected void updateInStorage(Resume r, Integer searchKey) {
        storage.set(searchKey, r);
    }

    @Override
    protected Resume getFromStorage(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void removeFromStorage(Integer searchKey) {
        storage.remove(searchKey.intValue());
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey != null;
    }

    @Override
    protected List<Resume> getListFromStorage() {
        return storage;
    }
}
