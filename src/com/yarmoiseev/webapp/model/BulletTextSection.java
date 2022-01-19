package com.yarmoiseev.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BulletTextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private final List<String> items;

    public BulletTextSection(String... items) {
        this(Arrays.asList(items));
    }

    public BulletTextSection(List<String> items) {
        Objects.requireNonNull(items, "Items mustn't be null");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(String s : items) {
            sb.append("*").append(s).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BulletTextSection that = (BulletTextSection) o;

        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return items != null ? items.hashCode() : 0;
    }
}
