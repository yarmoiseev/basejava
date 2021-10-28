package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int storageSize = 0;

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else System.out.println("Can't find. Resume with uuid: \"" + r.getUuid() + "\" doesn't exist");
    }

    public void save(Resume r) {
        int index = findIndex(r.getUuid());
        saveToStorage(r, index);
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.println("Can't find. Resume with uuid: \"" + uuid + "\" doesn't exist");
        return null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, storageSize - 1 - index);
            storageSize--;
        } else System.out.println("Can't find. Resume with uuid: \"" + uuid + "\" doesn't exist");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, storageSize);
    }

    public int size() {
        return storageSize;
    }

    protected abstract int findIndex(String uuid);

    public abstract void saveToStorage(Resume r, int index);

}
