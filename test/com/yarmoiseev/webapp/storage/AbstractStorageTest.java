package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.ResumeTestData;
import com.yarmoiseev.webapp.exception.ExistStorageException;
import com.yarmoiseev.webapp.exception.NotExistStorageException;
import com.yarmoiseev.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("D:\\GIT Repository\\baseJava\\basejava\\storage");

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
        storage.save(ResumeTestData.createResume(UUID_1, "full name"));
        storage.save(ResumeTestData.createResume(UUID_2, "full name"));
        storage.save(ResumeTestData.createResume(UUID_3, "full name"));
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
        Resume r = ResumeTestData.createResume(UUID_2, "full name");
        storage.update(r);
        assertTrue(r.equals(storage.get(UUID_2)));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(ResumeTestData.createResume(UUID_4, "full name"));
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> resumeList = new ArrayList<>();
        resumeList.add(ResumeTestData.createResume(UUID_1, "full name"));
        resumeList.add(ResumeTestData.createResume(UUID_2, "full name"));
        resumeList.add(ResumeTestData.createResume(UUID_3, "full name"));

        assertEquals(resumeList, storage.getAllSorted());
    }

    @Test
    public void save() throws Exception {
        Resume r = ResumeTestData.createResume(UUID_4, "full name");
        storage.save(r);
        assertEquals(r, storage.get(r.getUuid()));
        assertEquals(4, storage.size());
    }


    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() throws Exception {
        storage.save(ResumeTestData.createResume(UUID_2, "full name"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_4);
    }

    @Test
    public void get() throws Exception {
        Resume resume = ResumeTestData.createResume(UUID_2, "full name");
        Resume resume1 = storage.get(UUID_2);
        assertEquals(resume, resume1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_4);
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

}
