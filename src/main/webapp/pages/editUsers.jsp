<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Users</title>
    <link rel="stylesheet" href="${request.contextPath}/css/bootstrap.css">
</head>
<body>
<jsp:include page="menu.jsp"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="localization"/>
<h3 class="text-center"><fmt:message key="users"/></h3>
<table class="table">
    <thead class="thead-dark">
    <tr>
        <th scope="col"><c:out value="#"/></th>
        <th scope="col"><fmt:message key="u_email"/></th>
        <th scope="col"><fmt:message key="u_login"/></th>
        <th scope="col"><fmt:message key="u_password"/></th>
        <th scope="col"><fmt:message key="u_rating"/></th>
        <th scope="col"><fmt:message key="u_role"/></th>
    </tr>
    </thead>
    <tbody>
    <c:set var="counter" value="1"/>
    <c:forEach var="user" items="${users}">
        <tr>
            <form method="get" action="/main">
                <input type="hidden" name="command" value="editUser">

                <th scope="row"><c:out value="${counter}"/></th>
                <input type="hidden" name="userId" value="${user.id}">
                <c:set var="counter" value="${counter+1}"/>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.login}"/></td>
                <td><c:out value="${user.password}"/></td>
                <td><c:out value="${user.rating}"/></td>
                <td><c:out value="${user.roleString}"/></td>
                <td>
                    <button class="btn btn-dark btn-small"><fmt:message key="upcoming_edit"/></button>
                </td>
            </form>
            <td>
                <form method="get" action="/main">
                    <input type="hidden" name="command" value="deleteUser">
                    <input type="hidden" name="userId" value="${user.id}">
                    <button class="btn btn-default"><img src="../img/delete.png" height="20px" width="20px"></button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>

