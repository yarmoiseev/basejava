package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.exception.ExistStorageException;
import com.yarmoiseev.webapp.exception.NotExistStorageException;
import com.yarmoiseev.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ListStorageTest {

    private ListStorage storage = new ListStorage();

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() throws Exception {

        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
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
        Resume r = new Resume(UUID_2);
        storage.update(r);
        Assert.assertEquals(r, storage.get(UUID_2));
        Assert.assertEquals(3, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume("uuid4"));
    }

    @Test
    public void getAll() throws Exception {
        Resume[] expectedResumes = new Resume[]{
                new Resume(UUID_1),
                new Resume(UUID_2),
                new Resume(UUID_3)};

        Assert.assertArrayEquals(expectedResumes, storage.getAll());
    }

    @Test
    public void save() throws Exception {
        Resume r = new Resume("uuid4");
        storage.save(r);
        Assert.assertEquals(r, storage.get(r.getUuid()));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() throws Exception {
        storage.save(new Resume(UUID_2));
    }

    @Test
    public void delete() throws Exception {
        Resume[] expectedResumes = new Resume[]{
                new Resume(UUID_1),
                new Resume(UUID_3),};

        storage.delete(UUID_2);
        Assert.assertArrayEquals(expectedResumes, storage.getAll());
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("uuid4");
    }

    @Test
    public void get() throws Exception {
        Resume resume = new Resume(UUID_2);
        Assert.assertEquals(resume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("uuid4");
    }
}