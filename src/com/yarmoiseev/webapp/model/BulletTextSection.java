package com.yarmoiseev.webapp.model;

import java.util.List;

public class BulletTextSection extends Section {
    public List<String> items;

    public BulletTextSection(List<String> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(String s : items) {
            sb.append("*").append(s).append("\n");
        }
        return sb.toString();
    }
}
