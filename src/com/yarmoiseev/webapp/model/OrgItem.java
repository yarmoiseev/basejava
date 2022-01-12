package com.yarmoiseev.webapp.model;


import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.yarmoiseev.webapp.util.DateUtil.NOW;
import static com.yarmoiseev.webapp.util.DateUtil.of;


public class OrgItem {
    private final Link name;
    private List<OrgPeriod> periodsList = new ArrayList<>();

    public OrgItem(Link name, List<OrgPeriod> periodsList) {
        this.name = name;
        this.periodsList = periodsList;
    }

    public OrgItem(String name, String url, OrgPeriod... orgPeriod) {
        this(new Link(name, url), Arrays.asList(orgPeriod));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrgItem orgItem = (OrgItem) o;

        if (!Objects.equals(name, orgItem.name)) return false;
        return Objects.equals(periodsList, orgItem.periodsList);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (periodsList != null ? periodsList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name.toString()).append("\n");

        for (OrgPeriod p : periodsList) {
            sb.append(p.toString());
        }
        return sb.toString();
    }

    public static class OrgPeriod {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String title;
        private final String description;

        public OrgPeriod(int startYear,  Month startMonth, String title, String description) {
            this(of(startYear, startMonth), NOW, title, description);
        }

        public OrgPeriod(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), title, description);
        }

        public OrgPeriod(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "Start date mustn't be null");
            Objects.requireNonNull(endDate, "Ends date mustn't be null");
            Objects.requireNonNull(title, "Title mustn't be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
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
    }
}
