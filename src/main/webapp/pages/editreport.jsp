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
        <h2 class="text-center"><fmt:message key="editreport"/> </h2>
        <input type="hidden" name="command" value="editReport"/>
        <input type="hidden" name="reportid" value="${report.id}">
        <div class="form-group" >
            <input type="text" class="form-control" placeholder="${report.reportName}" name="reportName">
        </div>
        <div class="form-group">
            <select class="form-control" name="speakerid">
                <option value="0" selected disabled hidden>select speaker</option>
                <c:forEach var="speaker" items="${speakers}">
                    <option value="${speaker.id}"><c:out value="${speaker.login}"/></option>
                </c:forEach>
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
