package com.yarmoiseev.webapp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yarmoiseev.webapp.model.AbstractSection;
import com.yarmoiseev.webapp.storage.AbstractStorage;

import java.io.Reader;
import java.io.Writer;

public class JsonParser {
    private static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(AbstractSection.class, new JsonSectionAdapter())
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> T read(String string, Class<T> clazz) {
        return GSON.fromJson(GSON.toJson(string), clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }

    public static <T> String write(T object) {
        return GSON.toJson(object);
    };

    public static <T> String write(T object, Class<T> clazz) {
        return GSON.toJson(object, clazz);
    }

    public static String write(AbstractSection section, Class<AbstractStorage> abstractStorageClass) {
        return GSON.toJson(section, abstractStorageClass);
    }
}
