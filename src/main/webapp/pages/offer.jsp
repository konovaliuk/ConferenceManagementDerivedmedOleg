<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Offer report</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
          integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
</head>
<body>
<jsp:include page="menu.jsp"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="localization"/>
<form action="/main" method="post">
    <h2 class="text-center"><fmt:message key="menu_offerReport"/> </h2>
    <div class="form-group w-50" style="margin: auto;">
        <h4 class="text-center"><fmt:message key="selectConf"/>  </h4>
        <select class="form-control" id="select" name="confid">
            <c:forEach var="conf" items="${confs}">
                <option value="${conf.id}"><c:out value="${conf.name}"/></option>
            </c:forEach>
        </select>
    </div>
    <input type="hidden" name="command" value ="offer"/>
    <div class="form-group  w-25 p-3" style="margin: auto;">
        <input type="text" class="form-control" placeholder="Report Name" required="required" name="reportname">
    </div>
    <div class="form-group  w-25 p-3" style="margin: auto;">
        <textarea class="form-control" required="required" placeholder="Report Description" name="reportdesc"></textarea>
    </div>
    <div class="form-group w-25 p-3" style="margin: auto;">
        <button type="submit" class="btn btn-light"><fmt:message key="or_offer"/></button>
    </div>
</form>

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
