<%@ page import="com.yarmoiseev.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    <link href="https://fonts.googleapis.com/css2?family=Prosto+One&family=Russo+One&display=swap" rel="stylesheet">

    <title>Добавление нового резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="Имя"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value=""></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <input type="text" name="section" size=30 value="1"><br/>
        <input type="text" name="section" size=30 value="2"><br/>
        <input type="text" name="section" size=30 value="3"><br/>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
