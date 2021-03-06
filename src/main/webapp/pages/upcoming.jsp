<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upcoming confs</title>
    <link rel="stylesheet" href="${request.contextPath}/css/bootstrap.css">
</head>
<body>
<jsp:include page="menu.jsp"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="localization"/>
<ul class="list-group w-75" style="margin: auto; padding-top: 5px;">
    <c:forEach var="conf" items="${confs}">
        <c:if test="${'MODERATOR'==user.role}">
            <li class="text-left list-group-item">
                <input type="hidden" name="confid" value="${conf.id}">
                <a href="/main?command=editConf&confid=<c:out value="${conf.id}"/>" class="btn btn-dark"><fmt:message
                        key="upcoming_edit"/> </a>
                <form method="get" action="/main">
                    <input type="hidden" name="command" value="deleteConf">
                    <input type="hidden" name="confId" value="${conf.id}">
                    <button class="btn btn-default"><img src="../img/delete.png" height="20px" width="20px"></button>
                </form>
            </li>
        </c:if>
        <li class="list-group-item list-group-item-info text-center"><h4><c:out value="${conf.name}"/></h4>
        </li>
        <li class="list-group-item list-group-item-info text-center"><h5><c:out value="${conf.place}"/></h5></li>
        <li class="list-group-item list-group-item-info text-center"><h5><fmt:formatDate pattern="dd MMM yyyy HH:mm"
                                                                                         value="${conf.date}"/></h5>
        </li>
        <li class="list-group-item">
            <ul class="list-group">
                <c:forEach var="report" items="${conf.reports}">
                    <li class="list-group-item">
                        <div class="row">
                            <form method="post" class="col-10" action="/main">
                                <input type="hidden" name="command" value="regToRep">
                                <input type="hidden" name="reportId" value="${report.id}">
                                <div class="row">
                                    <div class="col-3">
                                        <p><c:out value="${report.reportName}"/></p>
                                        <p>Speaker : <c:out value="${report.speakerName}"/></p>
                                    </div>
                                    <div class="col-5">
                                        <p><c:out value="${report.reportDescription}"/></p>
                                    </div>
                                    <div class="col-4" style="text-align: right;">
                                        <p>
                                            <button class="btn btn-dark btn-sm" <c:out
                                                    value="${isRegistered.get(report.id)}"/>><fmt:message
                                                    key="upcoming_reg"/>
                                            </button>
                                        </p>
                                        <c:if test="${'MODERATOR'==user.role}">
                                            <input type="hidden" name="reportid" value="${report.id}">
                                            <p>
                                                <a class="btn btn-light btn-sm"
                                                   href="/main?command=editReport&reportid=<c:out value="${report.id}"/>"><fmt:message
                                                        key="upcoming_edit"/> </a>
                                            </p>
                                            <p>

                                                <a class="btn btn-light btn-sm"
                                                   href="/main?command=offerReport&reportid=<c:out value="${report.id}"/>"><fmt:message
                                                        key="upcoming_offer"/> </a>
                                            </p>
                                        </c:if>

                                    </div>
                                </div>
                            </form>

                            <div class="col-2">
                                <form method="get" action="/main">
                                    <input type="hidden" name="reportId" value="${report.id}">
                                    <input type="hidden" name="command" value="deleteReport">
                                    <c:if test="${'MODERATOR'==user.role}">
                                        <button class="btn btn-default"><img src="../img/delete.png" height="20px"
                                                                             width="20px"></button>
                                    </c:if>
                                </form>
                            </div>
                        </div>

                    </li>
                </c:forEach>
            </ul>
        </li>
    </c:forEach>
</ul>
</body>
</html>
