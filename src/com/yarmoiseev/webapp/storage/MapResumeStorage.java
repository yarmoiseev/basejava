package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
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
    protected void saveToStorage(Resume r, Object resumeAsKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateInStorage(Resume r, Object resumeAsKey) {
        storage.replace(r.getUuid(), r);
    }

    @Override
    protected Resume getFromStorage(Object resumeAsKey) {
        return (Resume) resumeAsKey;
    }

    @Override
    protected void removeFromStorage(Object resumeAsKey) {
        Resume r = (Resume) resumeAsKey;
        storage.remove(r.getUuid());
    }

    @Override
    protected Object getSearchKey(String uuid) {
            return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Object resumeAsKey) {
        return resumeAsKey != null;
    }

    @Override
    protected List<Resume> getListFromStorage() {
        return new ArrayList<>(storage.values());
    }
}
