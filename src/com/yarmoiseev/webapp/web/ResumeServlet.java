package com.yarmoiseev.webapp.web;

import com.yarmoiseev.webapp.Config;
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

    {
        try {
            sqlStorage = Config.get().getSqlStorage();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        List<Resume> resumeList = sqlStorage.getAllSorted();
        out.println("<table border=1 width=50% height=50%>");
        out.println("<tr><th>UUID</th><th>FULL NAME</th><tr>");
        for (Resume r : resumeList) {
            out.println("<tr><td>" + r.getUuid() + "</td><td>" + r.getFullName() + "</td></tr>");
        }
        out.println("</table>");
        out.println("</html></body>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
