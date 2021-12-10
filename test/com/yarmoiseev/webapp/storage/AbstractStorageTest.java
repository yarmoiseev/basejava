package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.exception.ExistStorageException;
import com.yarmoiseev.webapp.exception.NotExistStorageException;
import com.yarmoiseev.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

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
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume r = new Resume(UUID_2, "full name");
        storage.update(r);
        assertSame(r, storage.get(UUID_2));
        assertEquals(3, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume(UUID_4, "full name"));
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> resumeList = new ArrayList<>();
        resumeList.add(new Resume(UUID_1, "full name"));
        resumeList.add(new Resume(UUID_2, "full name"));
        resumeList.add(new Resume(UUID_3, "full name"));

        assertEquals(resumeList, storage.getAllSorted());
    }

    @Test
    public void save() throws Exception {
        Resume r = new Resume(UUID_4, "full name");
        storage.save(r);
        assertEquals(r, storage.get(r.getUuid()));
        assertEquals(4, storage.size());
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
        assertArrayEquals(expectedResumes, storage.getAllSorted().toArray());
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_4);
    }

    @Test
    public void get() throws Exception {
        Resume resume = new Resume(UUID_2, "full name");
        assertEquals(resume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_4);
    }
}