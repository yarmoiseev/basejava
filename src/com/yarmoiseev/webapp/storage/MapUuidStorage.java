package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
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
    protected void saveToStorage(Resume r, String searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateInStorage(Resume r, String searchKey) {
        storage.replace(searchKey, r);
    }

    @Override
    protected Resume getFromStorage(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void removeFromStorage(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected String getSearchKey(String uuid) {
        if (storage.get(uuid) != null) {
            return uuid;
        }
        return null;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return searchKey != null;
    }

    @Override
    protected List<Resume> getListFromStorage() {
        return new ArrayList<>(storage.values());
    }
}
