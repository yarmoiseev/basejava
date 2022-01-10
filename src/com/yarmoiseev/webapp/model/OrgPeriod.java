package com.yarmoiseev.webapp.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class OrgPeriod implements Comparable<OrgPeriod>{
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String description;

    public OrgPeriod(LocalDate startDate, LocalDate endDate, String title, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrgPeriod orgPeriod = (OrgPeriod) o;

        if (!Objects.equals(startDate, orgPeriod.startDate)) return false;
        if (!Objects.equals(endDate, orgPeriod.endDate)) return false;
        if (!Objects.equals(title, orgPeriod.title)) return false;
        return Objects.equals(description, orgPeriod.description);
    }

    @Override
    public int hashCode() {
        int result = startDate != null ? startDate.hashCode() : 0;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return startDate.toString() + " - " +
                endDate.toString() + "\n" + title + "\n" + description + "\n";
    }

    @Override
    public int compareTo(OrgPeriod p) {
        int result = Period.between(this.startDate, this.endDate).toString().
                compareTo(Period.between(p.startDate, p.endDate).toString());
        return result;
    }
}
