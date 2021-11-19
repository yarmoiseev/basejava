package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.exception.ExistStorageException;
import com.yarmoiseev.webapp.exception.NotExistStorageException;
import com.yarmoiseev.webapp.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, String> storage = new LinkedHashMap<>();

    @Override
    public void update(Resume r) {
        if (storage.containsKey(r.getUuid())) {
            storage.remove(r.getUuid());
            save(r);
        } else throw new NotExistStorageException(r.getUuid());
    }

    @Override
    public void save(Resume r) {
        if (!(storage.containsKey(r.getUuid()))) {
            storage.put(r.getUuid(), " name");
        } else throw new ExistStorageException(r.getUuid());
    }

    @Override
    public Resume get(String uuid) {
        if (!(storage.containsKey(uuid))) {
            throw new NotExistStorageException(uuid);
        }
        return new Resume(uuid + storage.get(uuid));
    }

    @Override
    public void delete(String uuid) {
        if (storage.containsKey(uuid)) {
            storage.remove(uuid);
        } else throw new NotExistStorageException(uuid);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Object[] objects = storage.keySet().toArray();
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

    @Override
    protected int findIndex(String uuid) {
        return 0;
    }

    @Override
    protected void setByIndex(int index, Resume r) {

    }

    @Override
    protected void addToStorage(Resume r) {

    }

    @Override
    protected Resume getByIndex(int index) {
        return null;
    }

    @Override
    protected void removeByIndex(int index) {

    }


}
