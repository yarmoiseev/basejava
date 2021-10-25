package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage implements Storage {

    public void clear() {
        Arrays.fill(storage, 0, storageSize, null);
        storageSize = 0;
    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index != -1) {
            storage[index] = r;
        } else System.out.println("Can't find. Resume with uuid: \"" + r.getUuid() + "\" doesn't exist");
    }

    public void save(Resume r) {
        if (findIndex(r.getUuid()) == -1) {
            if (storageSize != storage.length) {
                storage[storageSize] = r;
                storageSize++;
            } else System.out.println("The storage is full. Can't add new resume");
        } else System.out.println("These resume with uuid: \"" + r.getUuid() + "\" already exist");
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
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


    protected int findIndex(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
