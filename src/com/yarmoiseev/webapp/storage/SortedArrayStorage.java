package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void saveToStorage(Resume r, int index) {
        if (index < 0) {
            index = -(index) - 1;
            if (storageSize != storage.length) {
                System.arraycopy(storage, index, storage, index + 1, storageSize - index);
                storage[index] = r;
                storageSize++;
            } else System.out.println("The storage is full. Can't add new resume");
        } else System.out.println("These resume with uuid: \"" + r.getUuid() + "\" already exist");
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, storageSize, searchKey);
    }
}
