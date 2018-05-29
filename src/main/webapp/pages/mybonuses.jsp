<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>My Bonuses</title>
</head>
<body>
<jsp:include page="menu.jsp"/>
<div class="text-center">
    <p><c:out value="${user.login}"/>, you have <c:out value="${user.rating}"/> points.</p>
</div>
</body>
</html>
