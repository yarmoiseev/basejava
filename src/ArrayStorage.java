import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                storage[i] = null;
            } else break;
        }
    }

    void save(Resume r) {
        if (storage[9999] == null) {
            for (int i = 0; i < storage.length; i++) {
                if (storage[i] == null) {
                    storage[i] = r;
                    break;
                }
            }
        } else System.out.println("The storage is full. Can't add new Resume");
    }

    Resume get(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            try {
                if (storage[i].uuid.equals(uuid)) {
                    return storage[i];
                }
            } catch (NullPointerException nullPointerException) {
                System.out.println("Can't find resume " + uuid + " please, try again");
                break;
            }
        }
        return null;
    }

    void delete(String uuid) {
        Resume[] tempStorage = null;
        for (int i = 0; i < storage.length - 1; i++) {
            try {
                if (storage[i].uuid.equals(uuid)) {
                    tempStorage = new Resume[storage.length - 1];
                    for (int j = 0; j < i; j++) {
                        tempStorage[j] = storage[j];
                    }
                    for (int l = i; l < storage.length - 1; l++) {
                        tempStorage[l] = storage[l + 1];
                    }
                    break;
                }
            } catch (NullPointerException nullPointerException) {
                System.out.println("Can't find resume " + uuid + " please, try again");
                break;
            }
        }
        storage = tempStorage;
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
        int count = 0;
        for (Resume resume : storage) {
            if (resume != null) count++;
            else break;
        }
        return count;
    }
}
//TODO - use size() instead of length