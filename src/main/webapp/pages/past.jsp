<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Past confs</title>
    <link rel="stylesheet" href="${request.contextPath}/css/bootstrap.css">
</head>
<body>
<jsp:include page="menu.jsp"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="localization"/>
<ul class="list-group w-75" style="margin: auto; padding-top: 5px;">
    <c:forEach var="conf" items="${confs}">
        <li class="list-group-item list-group-item-info text-center"><h4><c:out value="${conf.name}"/></h4></li>
        <li class="list-group-item list-group-item-info text-center"><h5><c:out value="${conf.place}"/></h5></li>
        <li class="list-group-item list-group-item-info text-center"><h5><fmt:formatDate pattern="dd MMM yyyy HH:mm"
                                                                                         value="${conf.date}"/></h5>
        </li>
        <li class="list-group-item">
            <ul class="list-group">
                <c:forEach var="report" items="${conf.reports}">
                    <li class="list-group-item">
                        <form method="post" action="/main">
                            <input type="hidden" name="command" value="vote">
                            <input type="hidden" name="reportId" value="${report.id}">
                            <div class="row">
                                <div class="col-7">
                                    <p><c:out value="${report.reportName}"/></p>
                                    <p>Speaker : <c:out value="${report.speakerName}"/></p>
                                </div>
                                <div class="col-1" style="text-align: right;">
                                    Vote :
                                </div>
                                <div class="col-3">
                                    <div class="form-group">
                                        <select class="form-control" id="exampleFormControlSelect1" name="rating" <c:out
                                                value="${isRegistered.get(report.id)}"/>
                                                <c:out value="${isVoted.get(report.id)}"/>>
                                            <option value="1">1</option>
                                            <option value="2">2</option>
                                            <option value="3">3</option>
                                            <option value="4">4</option>
                                            <option value="5">5</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-1">
                                    <button class="btn btn-dark btn-sm" <c:out
                                            value="${isRegistered.get(report.id)}"/>
                                            <c:out value="${isVoted.get(report.id)}"/>><fmt:message key="vote"/>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </li>
                </c:forEach>
            </ul>
        </li>
    </c:forEach>
</ul>

</body>
</html>
