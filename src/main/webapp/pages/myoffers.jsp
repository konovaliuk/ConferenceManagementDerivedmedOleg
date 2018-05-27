<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Offers</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
          integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
</head>
<body>
<%@include file="menu.jsp" %>

<ul class="list-group w-75" style="margin: auto; padding-top: 5px;">
    <li class="list-group-item">
        <div class="row">
            <p>Offered by me : </p>
        </div>
        <ul class="list-group">
            <c:forEach var="report" items="${requestScope.reports}">
                <li class="list-group-item">
                    <div class="row">
                        <div class="col-3 list-group-item-info border border-info">
                            <b><c:out value="${report.confName}"/></b>
                        </div>
                        <div class="col-3">
                            <c:out value="${report.report_name}"/>
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
            <p>My offers : </p>
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
                                <c:out value="${report.report_name}"/>
                            </div>
                            <div class="col-3">
                                <fmt:formatDate pattern="dd MMM yyyy HH:mm"
                                                value="${report.date}"/>
                            </div>
                            <div class="col-3">
                                <c:if test="${!report.speakerName.equals(user.login)}">
                                    <button type="submit" class="btn btn-light">confirm offer</button>
                                </c:if>
                                <c:if test="${report.speakerName.equals(user.login)}">
                                    <c:out value="confirmed!"/>
                                </c:if>
                            </div>
                        </div>
                    </li>
                </form>
            </c:forEach>
        </ul>
    </li>
</ul>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"
        integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"
        integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm"
        crossorigin="anonymous"></script>
</body>
</html>
