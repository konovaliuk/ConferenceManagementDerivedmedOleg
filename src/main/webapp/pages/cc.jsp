<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit confs</title>
    <link rel="stylesheet" href="${request.contextPath}/css/bootstrap.css">
</head>
<body>
<jsp:include page="menu.jsp"/>
<fmt:setLocale value="${loc}"/>
<fmt:setBundle basename="localization"/>
<form action="/main" method="post" id="1">
    <h2 class="text-center"><fmt:message key="addreport"/> </h2>
    <div class="form-group w-50" style="margin: auto;">
        <h4 class="text-center"><fmt:message key="selectConf"/> </h4>
        <select class="form-control" id="select" name="confid" required="required">
            <c:forEach var="conf" items="${confs}">
                <option value="${conf.id}"><c:out value="${conf.name}"/></option>
            </c:forEach>
        </select>
    </div>
    <input type="hidden" name="command" value="add_report"/>
    <div class="form-group  w-25 p-3" style="margin: auto;">
        <input type="text" class="form-control" placeholder="Report Name" required="required" name="reportname">
    </div>
    <div class="form-group  w-25 p-3" style="margin: auto;">
        <textarea class="form-control" required="required" placeholder="Report Description"
                  name="reportdesc"></textarea>
    </div>
    <div class="form-group w-25 p-3" style="margin: auto;">
        <button type="submit" class="btn btn-light"><fmt:message key="add"/> </button>
    </div>
    <p style="color: red;" class="text-center">${requestScope.reportMessage}</p>
</form>
<form action="/main" method="post" id="2">
    <h2 class="text-center"><fmt:message key="addConference"/> </h2>
    <input type="hidden" name="command" value="addConf"/>
    <input type="hidden" name="confid" value="${conf.id}">
    <div class="form-group  w-25 p-3" style="margin: auto;">
        <input type="text" class="form-control" placeholder="<c:out value="name"/>" name="confName" required="required">
    </div>
    <div class="form-group  w-25 p-3" style="margin: auto;">
        <input type="text" class="form-control" placeholder="<c:out value="place"/>" name="confPlace"
               required="required">
    </div>
    <div class="form-group  w-25 p-3" style="margin: auto;">
        <c:out value="date :"/><input type="datetime-local" class="form-control" name="confDate" required="required"/>
    </div>
    <div class="form-group w-25 p-3" style="margin: auto;">
        <button type="submit" class="btn btn-light"><fmt:message key="add"/></button>
    </div>
    <p style="color: red;" class="text-center">${requestScope.message}</p>
</form>
</body>
</html>
