<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Offer to speaker</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
          integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
</head>
<body>
<jsp:include page="menu.jsp"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="localization"/>
<div style="text-align: center;">
    <form action="/main" method="post" class="w-25 text-center" style="margin: auto;">
        <h2 class="text-center"><fmt:message key="upcoming_offer"/> </h2>
        <input type="hidden" name="command" value="offerReport"/>
        <input type="hidden" name="reportid" value="${report.id}">
        <div class="form-group">
            <select class="form-control" name="speakerid">
                <option value="0" selected disabled hidden>select speaker</option>
                <c:forEach var="speaker" items="${speakers}">
                    <option value="${speaker.id}"><c:out value="${speaker.login}"/></option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group w-25 p-3" style="margin: auto;">
            <button type="submit" class="btn btn-light">offer</button>
        </div>
    </form>
</div>
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
