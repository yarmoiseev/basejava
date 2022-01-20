package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.exception.StorageException;
import com.yarmoiseev.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory mustn't be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory/not writable");
        }
    }

    @Override
    protected void saveToStorage(Resume r, Path path) {
        try {
            doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path" + path.getRoot(),
                    path.getFileName().toString(), e);
        }
    }

    @Override
    protected void updateInStorage(Resume r, Path path) {
        try {
            doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path writing error", r.getUuid(), e);
        }
    }

    @Override
    protected Resume getFromStorage(Path path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path reading error", path.getFileName().toString(), e);
        }
    }


    @Override
    protected void removeFromStorage(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path deleting error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toAbsolutePath().toString(), uuid);
    }

    @Override
    protected boolean isExist(Path path) { //TODO check
        return Files.exists(path);
    }

    @Override
    protected List<Resume> getListFromStorage() {
        List<Path> paths;
        try {
            paths = Files.list(directory).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory reading error", (Exception) null);
        }

        List<Resume> resumeList = new ArrayList<>(paths.size());
        for (Path p : paths) {
            resumeList.add(getFromStorage(p));
        }
        return resumeList;


    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::removeFromStorage);
        } catch (IOException e) {
            throw new StorageException("Path delete error", (Exception) null);
        }
    }

    @Override
    public int size() {
        String[] list = directory.toFile().list();
        if (list == null) {
            throw new StorageException("Directory reading error", (Exception) null);
        }
        return list.length;
    }

}
