package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    void saveDiffer() {
        storage = getSorted(storage);
    }

    protected Resume[] getSorted(Resume[] r) {
        Resume[] arr = r;
        for (int k = 1; k < storageSize; k++) {
            Resume newElement = arr[k];
            int index = findIndex(newElement.getUuid());
            if (index < 0) {
                index = -(index) - 1;
            }
            System.arraycopy(arr, index, arr, index + 1, k - index);
            arr[index] = newElement;
        }
        return arr;
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, storageSize, searchKey);
    }
}
