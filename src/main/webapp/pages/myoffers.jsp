<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Offers</title>
    <link rel="stylesheet" href="${request.contextPath}/css/bootstrap.css">
</head>
<body>
<jsp:include page="menu.jsp"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="localization"/>
<ul class="list-group w-75" style="margin: auto; padding-top: 5px;">
    <li class="list-group-item">
        <div class="row">
            <p><fmt:message key="mo_offeredByMe"/> </p>
        </div>
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
                        <div class="col-3">
                            <fmt:formatDate pattern="dd MMM yyyy HH:mm"
                                            value="${report.date}"/>
                        </div>
                        <div class="col-3">
                            <c:if test="${report.speakerName!=null}">
                                <c:out value="confirmed!"/>
                            </c:if>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </li>
    <li class="list-group-item">
        <div class="row">
            <p><fmt:message key="mo_myOffers"/> </p>
        </div>
        <ul class="list-group">
            <c:forEach var="report" items="${requestScope.reportsByModer}">
                <form method="post" action="/main">
                    <input type="hidden" name="command" value="myoffers">
                    <input type="hidden" name="reportid" value="${report.id}">
                    <li class="list-group-item">
                        <div class="row">
                            <div class="col-3 list-group-item-info border border-info">
                                <b><c:out value="${report.confName}"/></b>
                            </div>
                            <div class="col-3">
                                <c:out value="${report.reportName}"/>
                            </div>
                            <div class="col-3">
                                <fmt:formatDate pattern="dd MMM yyyy HH:mm"
                                                value="${report.date}"/>
                            </div>
                            <div class="col-3">
                                <c:if test="${!report.speakerName.equals(user.login)}">
                                    <button type="submit" class="btn btn-light"><fmt:message key="oc_confirmOffer"/> </button>
                                </c:if>
                                <c:if test="${report.speakerName.equals(user.login)}">
                                    <fmt:message key="oc_confirmed"/>
                                </c:if>
                            </div>
                        </div>
                    </li>
                </form>
            </c:forEach>
        </ul>
    </li>
</ul>
</body>
</html>
