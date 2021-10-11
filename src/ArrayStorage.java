import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int storageSize = 0;

    void clear() {
        for (int i = 0; i <= storageSize; i++) {
            storage[i] = null;
        }
        storageSize = 0;
    }

    void save(Resume r) {
        if (storageSize != storage.length) {
            storage[storageSize] = r;
            storageSize++;
        } else System.out.println("The storage is full. Can't add new Resume");
    }

    Resume get(String uuid) {
        for (int i = 0; i < storageSize; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < storageSize - 1; i++) {
            if (storage[i].uuid.equals(uuid)) {
                int l = storageSize;
                storageSize--;
                for (int j = i; j < l; j++) {
                    storage[j] = storage[j + 1];
                }
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] newStorage = new Resume[storageSize];
        for (int i = 0; i < storageSize; i++) {
            newStorage[i] = storage[i];
        }
        return newStorage;
    }

    int size() {
        return storageSize;
    }
}
