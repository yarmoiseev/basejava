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
//        Implementation with collections:
//        List<Resume> resumeList = new ArrayList<>(Arrays.asList(getAll()));
//        if (resumeList.contains(r)) {
//            storage[resumeList.indexOf(r)] = r;
//        } else System.out.println("Can't find. These resume doesn't exist");

        if (uuidExist(r.getUuid())) {
            for (int i = 0; i < storageSize; i++) {
                if (storage[i].getUuid().equals(r.getUuid())) {
                    storage[i] = r;
                }
            }
        } else System.out.println("Can't find. These resume doesn't exist");
    }

    public void save(Resume r) {
        if (!uuidExist(r.getUuid())) {
            if (storageSize != storage.length) {
                storage[storageSize] = r;
                storageSize++;
            } else System.out.println("The storage is full. Can't add new resume");
        } else System.out.println("These resume already exist");
    }

    public Resume get(String uuid) {
        if (uuidExist(uuid)) {
            for (int i = 0; i < storageSize; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    return storage[i];
                }
            }
        } else System.out.println("Can't find. These resume doesn't exist");
        return null;
    }

    public void delete(String uuid) {
        if (uuidExist(uuid)) {
            for (int i = 0; i < storageSize - 1; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    int l = storageSize;
                    storageSize--;
                    for (int j = i; j < l; j++) {
                        storage[j] = storage[j + 1];
                    }
                }
            }
        } else System.out.println("Can't find. These resume doesn't exist");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, storageSize);
    }

    public int size() {
        return storageSize;
    }

    public boolean uuidExist(String uuid) {
        boolean isFound = false;
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                isFound = true;
                break;
            }
        }
        return isFound;
    }
}
