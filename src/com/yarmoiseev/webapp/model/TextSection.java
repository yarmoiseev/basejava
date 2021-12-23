package com.yarmoiseev.webapp.model;

public class TextSection extends Section {
    public String text;

    public TextSection(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
