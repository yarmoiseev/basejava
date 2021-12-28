package com.yarmoiseev.webapp.model;

public class Link {
    private String name;
    private String url;

    public Link(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public String toString() {
        return name + " " + url;
    }
}
