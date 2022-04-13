<%@ page import="com.yarmoiseev.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    <link href="https://fonts.googleapis.com/css2?family=Prosto+One&family=Russo+One&display=swap" rel="stylesheet">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.yarmoiseev.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.MAIL.toHtml(resume.getContact(ContactType.MAIL))%></td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="${pageContext.request.contextPath}/img/delete.png"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="${pageContext.request.contextPath}/img/pencil.png"></a></td>
            </tr>
        </c:forEach>
    </table>

    <a href="resume?action=new" >Добавить резюме</a>

</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>