package com.yarmoiseev.webapp.storage;

import com.yarmoiseev.webapp.exception.StorageException;
import com.yarmoiseev.webapp.model.Resume;
import com.yarmoiseev.webapp.storage.serializestrategy.StreamSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;

    private StreamSerializer streamSerializer;

    protected PathStorage(String dir, StreamSerializer streamSerializer) {
        Objects.requireNonNull(dir, "directory mustn't be null");

        this.streamSerializer = streamSerializer;
        directory = Paths.get(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory/not writable");
        }
    }

    @Override
    protected void saveToStorage(Resume r, Path path) {
        try {
            Files.createFile(path);
            streamSerializer.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path" + path,
                    path.getFileName().toString(), e);
        }
    }

    @Override
    protected void updateInStorage(Resume r, Path path) {
        try {
            streamSerializer.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path writing error", r.getUuid(), e);
        }
    }

    @Override
    protected Resume getFromStorage(Path path) {
        try {
            return streamSerializer.doRead(new BufferedInputStream(Files.newInputStream(path)));
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
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) { //TODO check
        return Files.isRegularFile(path);
    }

    @Override
    protected List<Resume> getListFromStorage() {
        List<Path> paths;
        try {
            paths = Files.list(directory).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory reading error", null);
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
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        String[] list = directory.toFile().list();
        if (list == null) {
            throw new StorageException("Directory reading error", null);
        }
        return list.length;
    }

}
