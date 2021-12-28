package com.yarmoiseev.webapp.model;


import java.time.LocalDate;

public class OrgItem {
    private Link name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String description;

    public OrgItem(Link name, LocalDate startDate, LocalDate endDate, String title, String description) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public String toString() {

        return name.toString() + "\n" + startDate.toString() + " - " +
                endDate.toString() + "\n" + title + "\n" + description;
    }
}
