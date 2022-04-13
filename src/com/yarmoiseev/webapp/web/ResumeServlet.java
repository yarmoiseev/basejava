package com.yarmoiseev.webapp.web;

import com.yarmoiseev.webapp.Config;
import com.yarmoiseev.webapp.model.ContactType;
import com.yarmoiseev.webapp.model.Resume;
import com.yarmoiseev.webapp.storage.SqlStorage;

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
        Resume r;
        boolean save = false;
        if (uuid == null) {
            r = new Resume(UUID.randomUUID().toString(), fullName);
            save = true;
        } else {
            r = sqlStorage.get(uuid);
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
        if (!save) {
            sqlStorage.update(r);
        } else sqlStorage.save(r);

        response.sendRedirect("resume");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
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
            case "edit":
                r = sqlStorage.get(uuid);
                break;
            case "new":
                r = null;
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        switch (action) {
            case "view":
                request.getRequestDispatcher(("/WEB-INF/jsp/view.jsp")).forward(request, response);
                break;
            case "edit":
                request.getRequestDispatcher(("/WEB-INF/jsp/edit.jsp")).forward(request, response);
                break;
            case "new":
                request.getRequestDispatcher(("/WEB-INF/jsp/new.jsp")).forward(request, response);
        }
    }
}
