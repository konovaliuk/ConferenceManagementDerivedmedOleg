<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta content="text/html; charset=UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
          integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
</head>
<body class="hgu">
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="localization"/>
<ul class="nav justify-content-center navbar-light bg-light" style="margin-bottom: 10px;">
    <li class="btn btn-sm btn-light">
        <a class="nav-link" href="/main?command=main"><fmt:message key="menu_home"/> </a>
    </li>
    <li class="btn btn-sm btn-light">
        <a class="nav-link" href="/main?command=upcoming"><fmt:message key="menu_upcoming"/> </a>
    </li>
    <li class="btn btn-sm btn-light">
        <a class="nav-link" href="/main?command=past"><fmt:message key="menu_past"/> </a>
    </li>
    <li class="btn btn-sm btn-light">
        <a class="nav-link" href="/main?command=myconfs"><fmt:message key="menu_myConfs"/> </a>
    </li>
    <li class="btn btn-light btn-sm">
        <a class="nav-link" href="/main?command=ratings"><fmt:message key="menu_ratings"/> </a>
    </li>
    <c:if test="${user.role=='SPEAKER'}">
        <li class="btn btn-light btn-sm">
            <a class="nav-link" href="/main?command=offer"><fmt:message key="menu_offerReport"/> </a>
        </li>
        <li class="btn btn-light btn-sm">
            <a class="nav-link" href="/main?command=mybonuses"><fmt:message key="menu_myBonuses"/></a>
        </li>
        <li class="btn btn-light btn-sm">
            <a class="nav-link" href="/main?command=myoffers"><fmt:message key="menu_myOffers"/></a>
        </li>
    </c:if>
    <c:if test="${user.role=='ADMINISTRATOR'}">
        <li class="btn btn-light btn-sm">
            <a class="nav-link" href="#"><fmt:message key="menu_edit"/></a>
        </li>
    </c:if>
    <c:if test="${user.role=='MODERATOR'}">
        <li class="btn btn-light btn-sm">
            <a class="nav-link" href="/main?command=add_report"><fmt:message key="menu_cc"/></a>
        </li>
        <li class="btn btn-light btn-sm">
            <a class="nav-link" href="/main?command=offersControl"><fmt:message key="menu_oc"/></a>
        </li>
    </c:if>
    <li class="btn justify-content-left btn-light btn-sm">
        <a class="nav-link" href="/main?command=logOut"><fmt:message key="menu_logout"/></a>
    </li>
    <li>
        <a href="/main?locale=ru&command=locale"><fmt:message key="menu_ru"/></a>
    </li>
    <li>
        <a href="/main?locale=en&command=locale"><fmt:message key="menu_en"/></a>
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
