package com.yarmoiseev.webapp.web;

import com.yarmoiseev.webapp.ResumeTestData;
import com.yarmoiseev.webapp.model.Resume;
import com.yarmoiseev.webapp.storage.SqlStorage;
import com.yarmoiseev.webapp.storage.Storage;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

public class ResumeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type","text/html; charset=UTF-8");


        response.getWriter().write(" <table>\n" +
                "  <tr>\n" +
                "    <th>UUID</th>\n" +
                "    <th>Full name</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>396bf2d8-07d2-48de-8c6a-3792cfb97adf</td>\n" +
                "    <td>full name1</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>d67b9ab4-d6e0-4b56-9a98-1809e1b43c19</td>\n" +
                "    <td>full name2</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>4a9fe023-8f73-4623-9d28-cb5023565389</td>\n" +
                "    <td>full name3</td>\n" +
                "  </tr>\n" +
                "</table> ");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
