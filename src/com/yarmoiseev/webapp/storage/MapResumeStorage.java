package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
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
    protected void saveToStorage(Resume r, Resume resumeAsKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void updateInStorage(Resume r, Resume resumeAsKey) {
        storage.replace(r.getUuid(), r);
    }

    @Override
    protected Resume getFromStorage(Resume resumeAsKey) {
        return resumeAsKey;
    }

    @Override
    protected void removeFromStorage(Resume resumeAsKey) {
        Resume r = resumeAsKey;
        storage.remove(r.getUuid());
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume resumeAsKey) {
        return resumeAsKey != null;
    }

    @Override
    protected List<Resume> getListFromStorage() {
        return new ArrayList<>(storage.values());
    }
}
