package com.yarmoiseev.webapp.model;

import java.util.List;

public class OrganizationListSection extends AbstractSection {
    private List<OrgItem> items;

    public OrganizationListSection(List<OrgItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (OrgItem o : items) {
            sb.append(o.toString()).append("\n");
        }
        return sb.toString();
    }
}
