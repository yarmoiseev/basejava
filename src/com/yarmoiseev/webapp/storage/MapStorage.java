package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storage = new LinkedHashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Object[] objects = storage.values().toArray();
        Resume[] resumes = new Resume[storage.size()];
        for (int i = 0; i < objects.length; i++) {
            resumes[i] = new Resume(objects[i].toString());
        }
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }

    protected int findIndex(String uuid) {
      return storage.containsKey(uuid) ? 0 : -1;
    }

    protected void setByIndex(int index, Resume r) {
        save(r);
    }

    protected void addToStorage(Resume r) {
        storage.put(r.getUuid(), r);
    }

    protected Resume getByIndex(String uuid) {
        return storage.get(uuid);
    }

    protected void removeByIndex(String uuid) {
        storage.remove(uuid);
    }
}
