package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> storage = new LinkedHashMap<>();

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
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateInStorage(Resume r, Object searchKey) {
        storage.replace((String) searchKey, r);
    }

    @Override
    protected Resume getFromStorage(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void removeFromStorage(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        if (storage.get(uuid) != null) {
            return uuid;
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected List<Resume> getListFromStorage() {
        return new ArrayList<>(storage.values());
    }
}
