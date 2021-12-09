package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.exception.ExistStorageException;
import com.yarmoiseev.webapp.exception.NotExistStorageException;
import com.yarmoiseev.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1, "full name"));
        storage.save(new Resume(UUID_2, "full name"));
        storage.save(new Resume(UUID_3, "full name"));
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume r = new Resume(UUID_2, "full name");
        storage.update(r);
        Assert.assertEquals(r, storage.get(UUID_2));
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume("uuid4", "full name"));
    }

    @Test
    public void getAll() throws Exception {
        Resume[] expectedResumes = new Resume[]{
                new Resume(UUID_1, "full name"),
                new Resume(UUID_2, "full name"),
                new Resume(UUID_3, "full name")};

        Assert.assertArrayEquals(expectedResumes, storage.getAllSorted().toArray());
    }

    @Test
    public void save() throws Exception {
        Resume r = new Resume("uuid4", "full name");
        storage.save(r);
        Assert.assertEquals(r, storage.get(r.getUuid()));
        Assert.assertEquals(4, storage.size());
    }


    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() throws Exception {
        storage.save(new Resume(UUID_2, "full name"));
    }

    @Test
    public void delete() throws Exception {
        Resume[] expectedResumes = new Resume[]{
                new Resume(UUID_1, "full name"),
                new Resume(UUID_3, "full name"),};

        storage.delete(UUID_2);
        Assert.assertArrayEquals(expectedResumes, storage.getAllSorted().toArray());
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("uuid4");
    }

    @Test
    public void get() throws Exception {
        Resume resume = new Resume(UUID_2, "full name");
        Assert.assertEquals(resume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("uuid4");
    }
}
