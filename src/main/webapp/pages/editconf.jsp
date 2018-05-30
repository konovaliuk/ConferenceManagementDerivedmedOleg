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
<form action="/main" method="post">
    <h2 class="text-center"><fmt:message key="editconf"/> </h2>
    <input type="hidden" name="command" value ="editConf"/>
    <input type="hidden" name="confid" value="${conf.id}">
    <div class="form-group  w-25 p-3" style="margin: auto;">
        <input type="text" class="form-control" placeholder="${conf.name}" name="confName">
    </div>
    <div class="form-group  w-25 p-3" style="margin: auto;">
        <input type="text" class="form-control" placeholder="${conf.place}" name="confPlace">
    </div>
    <div class="form-group  w-25 p-3" style="margin: auto;">
        <input type="datetime-local" class="form-control" name="confDate" placeholder="${conf.date}"/>
    </div>
    <div class="form-group w-25 p-3" style="margin: auto;">
        <button type="submit" class="btn btn-light">Edit</button>
    </div>
    <p style="color: red;" class="text-center">${requestScope.message}</p>
</form>
</body>
</html>
