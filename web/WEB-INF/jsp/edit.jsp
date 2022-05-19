<%@ page import="com.yarmoiseev.webapp.model.ContactType" %>
<%@ page import="com.yarmoiseev.webapp.model.SectionType" %>
<%@ page import="com.yarmoiseev.webapp.model.BulletTextSection" %>
<%@ page import="com.yarmoiseev.webapp.model.OrganizationListSection" %>
<%@ page import="com.yarmoiseev.webapp.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name= "viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
    <jsp:useBean id="resume" type="com.yarmoiseev.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <h1>ФИО</h1>
        <dl>
            <input type="text" name="fullName" pattern="[A-Za-z][А-Яа-я]{30}" size=55 value="${resume.fullName}" required>
        </dl>
        <h2>Контакты:</h2>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <c:if test="${type eq ContactType.MAIL}">
                    <dd><input type="email" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
                </c:if>
                <c:if test="${type ne ContactType.MAIL}">
                    <dd><input type="text" name="${type.name()}" size=40 value="${resume.getContact(type)}"></dd>
                </c:if>
            </dl>
        </c:forEach>
        <hr>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="com.yarmoiseev.webapp.model.AbstractSection"/>
            <h2><a>${type.title}</a></h2>
            <c:choose>
                <c:when test="${type=='OBJECTIVE' || type=='PERSONAL'}">
                    <textarea name='${type}' cols=75 rows=5><%=section%></textarea>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <textarea name='${type}' cols=75
                              rows=5><%=String.join("\n", ((BulletTextSection) section).getItems())%></textarea>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="orgItem" items="<%=((OrganizationListSection) section).getItems()%>"
                               varStatus="counter">
                        <dl>
                            <dt>Название учереждения:</dt>
                            <dd><input type="text" name='${type}' size=100 value="${fn:escapeXml(orgItem.name.name)}"></dd>
                        </dl>
                        <dl>
                            <dt>Сайт учереждения:</dt>
                            <dd><input type="text" name='${type}url' size=100 value="${orgItem.name.url}"></dd>
                        </dl>
                        <br>
                        <div style="margin-left: 30px">
                            <c:forEach var="period" items="${orgItem.periodsList}">
                                <jsp:useBean id="period" type="com.yarmoiseev.webapp.model.OrgItem.OrgPeriod"/>
                                    <dl>
                                        <dt>Начальная дата:</dt>
                                        <dd>
                                            <input type="text" name="${type}${counter.index}startDate" size=10
                                                   value="<%=DateUtil.format(period.getStartDate())%>" placeholder="MM/yyyy">
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt>Конечная дата:</dt>
                                        <dd>
                                            <input type="text" name="${type}${counter.index}endDate" size=10
                                                   value="<%=DateUtil.format(period.getEndDate())%>" placeholder="MM/yyyy">
                                    </dl>
                                    <dl>
                                        <dt>Должность:</dt>
                                        <dd><input type="text" name='${type}${counter.index}title' size=75
                                                   value="${fn:escapeXml(period.title)}">
                                    </dl>
                                    <dl>
                                        <dt>Описание:</dt>
                                        <dd><textarea name="${type}${counter.index}description" rows=5
                                                      cols=75>${fn:escapeXml(period.description)}</textarea></dd>
                                    </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>

            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>