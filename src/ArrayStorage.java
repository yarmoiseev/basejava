import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int storageSize = size();

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                storage[i] = null;
                storageSize -= 1;
            } else break;
        }
    }

    void save(Resume r) {
        if (storageSize != storage.length) {
            for (int i = 0; i < storage.length; i++) {
                if (storage[i] == null) {
                    storage[i] = r;
                    storageSize += 1;
                    break;
                }
            }
        } else System.out.println("The storage is full. Can't add new Resume");
    }

    Resume get(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null && storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.length - 1; i++) {
            if (storage[i] != null && storage[i].uuid.equals(uuid)) {
                storageSize -= 1;
                for (int j = i; j < storage.length - 1; j++) {
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
        int count = 0;
        for (Resume resume : storage) {
            if (resume != null) count++;
            else break;
        }
        return count;
    }
}
