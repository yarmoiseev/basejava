package com.yarmoiseev.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class BulletTextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    public static final BulletTextSection EMPTY = new BulletTextSection("");

    private List<String> items;

    public BulletTextSection() {
    }

    public BulletTextSection(String... items) {
        this(Arrays.asList(removeEmpty(items)));
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
            sb.append(s).append("\n");
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

    public static String[] removeEmpty(String[] stringArray) {
        return Arrays.stream(stringArray)
                .filter(value ->
                        value != null && value.length() > 0 && !value.equals("\r")
                )
                .toArray(String[]::new);
    }
}
