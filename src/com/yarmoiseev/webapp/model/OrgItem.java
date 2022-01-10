package com.yarmoiseev.webapp.model;


import java.util.SortedSet;

public class OrgItem {
    private Link name;
    private SortedSet<OrgPeriod> periodsSet;

    public OrgItem(Link name, SortedSet<OrgPeriod> periodsSet) {
        this.name = name;
        this.periodsSet = periodsSet;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name.toString() + "\n");

        for (OrgPeriod p : periodsSet) {
            sb.append(p.toString());
        }
        return sb.toString();
    }
}
