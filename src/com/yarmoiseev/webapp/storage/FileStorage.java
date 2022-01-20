package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.exception.StorageException;
import com.yarmoiseev.webapp.model.Resume;
import com.yarmoiseev.webapp.storage.serializestrategy.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private File directory;

    private StreamSerializer streamSerializer;

    protected FileStorage(File directory, StreamSerializer streamSerializer) {
        Objects.requireNonNull(directory, "directory mustn't be null");

        this.streamSerializer = streamSerializer;

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writeable");
        }
        this.directory = directory;
    }

    @Override
    protected void saveToStorage(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        updateInStorage(r, file);
    }


    @Override
    protected void updateInStorage(Resume r, File file) {
        try {
            streamSerializer.doWrite(r, new BufferedOutputStream(new FileOutputStream((file))));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume getFromStorage(File file) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File reading error", file.getName(), e);
        }
    }


    @Override
    protected void removeFromStorage(File file) {
        if (!file.delete()) {
            throw new StorageException("File deleting error", file.getName());
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> getListFromStorage() {
        File[] files = getFiles();

        List<Resume> resumeList = new ArrayList<>(files.length);
        for (File file : files) {
            resumeList.add(getFromStorage(file));
        }
        return resumeList;
    }

    @Override
    public void clear() {
        for (File file : getFiles()) {
            removeFromStorage(file);
        }
    }

    @Override
    public int size() {
        return getFiles().length;
    }

    private File[] getFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Error: Directory is empty", (Exception) null);
        }

        return files;
    }
}
