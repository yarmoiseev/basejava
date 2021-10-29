package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void saveToStorage(Resume r, int index) {
        index = -(index) - 1;
        System.arraycopy(storage, index, storage, index + 1, storageSize - index);
        storage[index] = r;
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, storageSize, searchKey);
    }
}
