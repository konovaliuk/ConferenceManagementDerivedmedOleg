<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Auth</title>
    <link rel="stylesheet" href="${request.contextPath}/css/bootstrap.css">
</head>
<body>
<div class="login-form" style="text-align: center;">
    <form action="/main" method="post">
        <input type="hidden" name="command" value ="registration"/>
        <h2 class="text-center">Registration</h2>
        <div class="form-group  w-25 p-3" style="margin: auto;">
            <input type="email" class="form-control" placeholder="Email" required="required" name="email">
        </div>
        <div class="form-group  w-25 p-3" style="margin: auto;">
            <input type="text" class="form-control" placeholder="Username" required="required" name="username" pattern="^[а-яА-ЯёЁa-zA-Z0-9\s{0,1}]+$">
        </div>
        <div class="form-group  w-25 p-3" style="margin: auto;">
            <input type="password" class="form-control" placeholder="Password" required="required" name="password" pattern="^[a-zA-Z0-9]+$">
        </div>
        <div class="form-group w-25 p-3" style="margin: auto;">
            <button type="submit" class="btn btn-light">Register</button>
        </div>
    </form>
    <p style="color: red;" class="text-center">${requestScope.message}</p>
</div>
</body>

</html>
