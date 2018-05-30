<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Offer report</title>
    <link rel="stylesheet" href="${request.contextPath}/css/bootstrap.css">
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

</body>
</html>
