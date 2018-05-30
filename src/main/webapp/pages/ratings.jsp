<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Ratings</title>
    <link rel="stylesheet" href="${request.contextPath}/css/bootstrap.css">
</head>
<body>
<jsp:include page="menu.jsp"/>
<table class="table w-50" style="margin: auto;">
    <thead>
    <tr style="margin: auto;">
        <th scope="col">#</th>
        <th scope="col">NickName</th>
        <th scope="col">Rating</th>
    </tr>
    </thead>
    <tbody>
    <c:set var="index" value="${1}"/>
    <c:forEach var="user" items="${requestScope.ratings}">
        <tr style="margin: auto;">
            <th scope="row"><c:out value="${index}"/></th>
            <td><c:out value="${user.login}"/></td>
            <td><c:out value="${user.rating}"/></td>
        </tr>
        <c:set var="index" value="${index+1}"/>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
