package com.yarmoiseev.webapp.util;

import com.yarmoiseev.webapp.model.OrgItem;

public class HtmlUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String formatDates(OrgItem.OrgPeriod period) {
        return DateUtil.format(period.getStartDate()) + " - " + DateUtil.format(period.getEndDate());
    }
}
