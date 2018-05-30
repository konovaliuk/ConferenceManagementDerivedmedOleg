<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
    <link rel="stylesheet" href="${request.contextPath}/css/bootstrap.css">
</head>
<body>
<jsp:include page="menu.jsp"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="localization"/>
<div style="text-align: center;">
    <form action="/main" method="post" class="w-25 text-center" style="margin: auto;">
        <h2 class="text-center"><fmt:message key="edit_user"/> </h2>
        <input type="hidden" name="command" value="editUser"/>
        <input type="hidden" name="userId" value="${userId}">
        <div class="form-group" >
            <input type="text" class="form-control" name="userEmail" placeholder="email">
            <input type="text" class="form-control" name="userLogin" placeholder="login">
            <input type="text" class="form-control" name="userPassword" placeholder="password">
            <input type="text" class="form-control" name="userRating" placeholder="rating">
        </div>
        <div class="form-group">
            <select class="form-control" name="role">
                <option value="0" selected disabled hidden>choose role</option>
                    <option value="ADMINISTRATOR"><c:out value="admin"/></option>
                    <option value="MODERATOR"><c:out value="moder"/></option>
                    <option value="SPEAKER"><c:out value="speaker"/></option>
                    <option value="USER"><c:out value="user"/></option>
            </select>
        </div>
        <div class="form-group w-25 p-3" style="margin: auto;">
            <button type="submit" class="btn btn-light">Edit</button>
        </div>
        <p style="color: red;" class="text-center">${requestScope.message}</p>
    </form>
</div>
</body>
</html>
