package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * ArrayList based storage for Resumes
 */

public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[storage.size()];
        resumes = storage.toArray(resumes);
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int findIndex(String uuid) {
        Resume resume = new Resume(uuid);
        return storage.indexOf(resume);
    }

    @Override
    protected void setByIndex(int index, Resume r) {
        storage.set(index, r);
    }

    @Override
    protected void addToStorage(Resume r) {
        storage.add(r);
    }

    @Override
    protected Resume getByIndex(int index) {
        return storage.get(index);
    }

    @Override
    protected void removeByIndex(int index) {
        storage.remove(index);
    }
}
