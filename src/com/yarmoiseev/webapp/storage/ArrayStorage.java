package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int storageSize = 0;

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    public void update(Resume r) {
        if (uuidExist(r.getUuid()) != null) {
            storage[uuidExist(r.getUuid())] = r;
        } else System.out.println("Can't find. Resume with uuid: \"" + r.getUuid() + "\" doesn't exist");
    }

    public void save(Resume r) {
        if (uuidExist(r.getUuid()) == null) {
            if (storageSize != storage.length) {
                storage[storageSize] = r;
                storageSize++;
            } else System.out.println("The storage is full. Can't add new resume");
        } else System.out.println("These resume with uuid: \"" + r.getUuid() + "\" already exist");
    }

    public Resume get(String uuid) {
        if (uuidExist(uuid) != null) {
            return storage[uuidExist(uuid)];
        }
        System.out.println("Can't find. Resume with uuid: \"" + uuid + "\" doesn't exist");
        return null;
    }

    public void delete(String uuid) {
        if (uuidExist(uuid) != null) {
            int l = storageSize;
            storageSize--;
            for (int j = uuidExist(uuid); j < l; j++) {
                storage[j] = storage[j + 1];
            }
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

    public Integer uuidExist(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }
}
