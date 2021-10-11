import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int storageSize = 0;

    void clear() {
        for (int i = 0; i <= size(); i++) {
                storage[i] = null;
                storageSize--;
        }
    }

    void save(Resume r) {
        if (storageSize != storage.length) {
            for (int i = 0; i < storage.length; i++) {
                if (storage[i] == null) {
                    storage[i] = r;
                    storageSize++;
                    break;
                }
            }
        } else System.out.println("The storage is full. Can't add new Resume");
    }

    Resume get(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size() - 1; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storageSize--;
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
        Resume[] newStorage = new Resume[size()];
        for (int i = 0; i < size(); i++) {
            newStorage[i] = storage[i];
        }
        return newStorage;
    }

    int size() {
        return storageSize;
    }
}
