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
<h3 class="text-center">Offered by speakers</h3>
<table class="table">
    <thead class="thead-dark">
    <tr>
        <th scope="col"><c:out value="#"/></th>
        <th scope="col"><c:out value="Speaker"/></th>
        <th scope="col"><c:out value="Conf name"/></th>
        <th scope="col"><c:out value="Report name"/></th>
        <th scope="col"><c:out value="Conf Date"/></th>
        <th scope="col"><c:out value="Confirmation"/></th>
    </tr>
    </thead>
    <tbody>
    <c:set var="counter" value="1"/>
    <c:forEach var="report" items="${reports}">
        <form method="post" action="/main">
            <input type="hidden" name="command" value="offersControl">
            <tr>
                <th scope="row"><c:out value="${counter}"/></th>
                <<input type="hidden" name="userId" value="${report.userId}">
                <<input type="hidden" name="reportId" value="${report.reportId}">
                <<input type="hidden" name="confId" value="${report.confId}">
                <c:set var="counter" value="${counter+1}"/>
                <td><c:out value="${report.speakerName}"/></td>
                <td><c:out value="${report.confName}"/></td>
                <td><c:out value="${report.reportName}"/></td>
                <td><fmt:formatDate pattern="dd MMM yyyy HH:mm" value="${report.confDate}"/></td>
                <td>
                    <button class="btn btn-dark btn-small">Confirm</button>
                </td>
            </tr>
        </form>
    </c:forEach>
    </tbody>
</table>
<h3 class="text-center">Confirmed</h3>
<table class="table">
    <thead class="thead-light">
    <tr>
        <th scope="col"><c:out value="#"/></th>
        <th scope="col"><c:out value="Speaker"/></th>
        <th scope="col"><c:out value="Conf name"/></th>
        <th scope="col"><c:out value="Report name"/></th>
        <th scope="col"><c:out value="Conf Date"/></th>
        <th scope="col"><c:out value="Confirmation"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="report" items="${confirmed}">
        <tr>
            <th scope="row"><c:out value="${counter}"/></th>
            <<input type="hidden" name="userId" value="${report.userId}">
            <<input type="hidden" name="reportId" value="${report.reportId}">
            <<input type="hidden" name="confId" value="${report.confId}">
            <c:set var="counter" value="${counter+1}"/>
            <td><c:out value="${report.speakerName}"/></td>
            <td><c:out value="${report.confName}"/></td>
            <td><c:out value="${report.reportName}"/></td>
            <td><fmt:formatDate pattern="dd MMM yyyy HH:mm" value="${report.confDate}"/></td>
            <td><c:out value="confirmed!"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
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
