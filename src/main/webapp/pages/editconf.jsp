<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
          integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
</head>
<body>
<%@include file="menu.jsp" %>
<form action="/main" method="post">
    <h2 class="text-center">Edit conference</h2>
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
