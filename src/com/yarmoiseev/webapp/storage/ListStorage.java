package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * ArrayList based storage for Resumes
 */

public class ListStorage extends AbstractStorage {
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
    protected void saveToStorage(Resume r, Object searchKey) {
        storage.add(r);
    }

    @Override
    protected void updateInStorage(Resume r, Object searchKey) {
        storage.set((Integer) searchKey, r);
    }

    @Override
    protected Resume getFromStorage(Object searchKey) {
        return storage.get((Integer) searchKey);
    }

    @Override
    protected void removeFromStorage(Object searchKey) {
        storage.remove(((Integer) searchKey).intValue());
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected List<Resume> getListFromStorage() {
        return storage;
    }
}
