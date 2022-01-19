package com.yarmoiseev.webapp.model;

import java.io.Serializable;
import java.util.Objects;

public class Link implements Serializable {
    private String name;
    private String url;

    public Link(String name, String url) {
        Objects.requireNonNull(name, "Name mustn't be null");
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return name + " " + url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (!Objects.equals(name, link.name)) return false;
        return Objects.equals(url, link.url);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }
}
