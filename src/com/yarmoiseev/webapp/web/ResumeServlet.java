package com.yarmoiseev.webapp.web;

import com.yarmoiseev.webapp.Config;
import com.yarmoiseev.webapp.model.*;
import com.yarmoiseev.webapp.storage.SqlStorage;
import com.yarmoiseev.webapp.util.DateUtil;
import com.yarmoiseev.webapp.util.HtmlUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;

public class ResumeServlet extends HttpServlet {
    private static SqlStorage sqlStorage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            sqlStorage = Config.get().getSqlStorage();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        final boolean isCreate = (uuid == null || uuid.length() == 0);
        Resume r;
        if (isCreate) {
            r = new Resume(fullName);
        } else {
            r = sqlStorage.get(uuid);
            r.setFullName(fullName);
        }

        r.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                r.addContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        /* Add sections */
        for (SectionType sectionType : SectionType.values()) {
            String value = request.getParameter(sectionType.name());
            String[] values = request.getParameterValues(sectionType.name());
            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                r.getSections().remove(sectionType);
            } else {
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        r.addSection(sectionType, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        r.addSection(sectionType, new BulletTextSection(value.split("\\n")));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<OrgItem> orgItems = new ArrayList<>();
                        String[] urls = request.getParameterValues(sectionType.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!HtmlUtil.isEmpty(name)) {
                                List<OrgItem.OrgPeriod> positions = new ArrayList<>();
                                String pfx = sectionType.name() + i;
                                String[] startDates = request.getParameterValues(pfx + "startDate");
                                String[] endDates = request.getParameterValues(pfx + "endDate");
                                String[] titles = request.getParameterValues(pfx + "title");
                                String[] descriptions = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!HtmlUtil.isEmpty(titles[j])) {
                                        positions.add(new OrgItem.OrgPeriod(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), titles[j], descriptions[j]));
                                    }
                                }
                                orgItems.add(new OrgItem(new Link(name, urls[i]), positions));
                            }
                        }
                        r.addSection(sectionType, new OrganizationListSection(orgItems));
                        break;
                }
            }

        }

        if (isCreate) {
            sqlStorage.save(r);
        } else {
            sqlStorage.update(r);
        }

        response.sendRedirect("resume");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", sqlStorage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                sqlStorage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                r = sqlStorage.get(uuid);
                break;
            case "add":
                r = Resume.EMPTY;
                break;
            case "edit":
                r = sqlStorage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = r.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = BulletTextSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationListSection orgSection = (OrganizationListSection) section;
                            List<OrgItem> emptyFirstOrganizations = new ArrayList<>();
                            emptyFirstOrganizations.add(OrgItem.EMPTY);
                            if (orgSection != null) {
                                for (OrgItem org : orgSection.getItems()) {
                                    List<OrgItem.OrgPeriod> emptyFirstPeriod = new ArrayList<>();
                                    emptyFirstPeriod.add(OrgItem.OrgPeriod.EMPTY);
                                    emptyFirstPeriod.addAll(org.getPeriodsList());
                                    emptyFirstOrganizations.add(new OrgItem(org.getName(), emptyFirstPeriod));
                                }
                            }
                            section = new OrganizationListSection(emptyFirstOrganizations);
                            break;
                    }
                    r.addSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

}
