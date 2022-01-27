package com.yarmoiseev.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<OrgItem> items;

    public OrganizationListSection() {
    }

    public OrganizationListSection(OrgItem... item) {
        this(Arrays.asList(item));
    }

    public OrganizationListSection(List<OrgItem> items) {
        Objects.requireNonNull(items, "Items mustn't be null");
        this.items = items;
    }

    public List<OrgItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (OrgItem o : items) {
            sb.append(o.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationListSection that = (OrganizationListSection) o;

        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return items != null ? items.hashCode() : 0;
    }
}
