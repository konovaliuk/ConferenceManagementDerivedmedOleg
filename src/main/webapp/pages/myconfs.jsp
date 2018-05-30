<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>My Confs</title>
    <link rel="stylesheet" href="${request.contextPath}/css/bootstrap.css">
</head>
<body>
<jsp:include page="menu.jsp"/>
<ul class="list-group w-75" style="margin: auto; padding-top: 5px;">
    <li class="list-group-item">
        <ul class="list-group">
            <c:forEach var="report" items="${requestScope.reports}">
                <li class="list-group-item">
                    <div class="row">
                        <div class="col-3 list-group-item-info border border-info">
                            <b><c:out value="${report.confName}"/></b>
                        </div>
                        <div class="col-3">
                            <c:out value="${report.reportName}"/>
                        </div>
                        <div class="col-6" style="text-align: right">
                            <c:out value="${report.date}"/>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </li>
</ul>
</body>
</html>
