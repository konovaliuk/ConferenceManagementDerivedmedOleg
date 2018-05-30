<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Here</title>
    <link rel="stylesheet" href="${request.contextPath}/css/bootstrap.css">
</head>
<body>
<div class="login-form" style="text-align: center;">
    <form action="/main" method="post">
        <h2 class="text-center">Log in</h2>
        <div class="form-group  w-25 p-3" style="margin: auto;">
            <input type="email" class="form-control" placeholder="Email" required="required" name="login">
        </div>
        <div class="form-group  w-25 p-3" style="margin: auto;">
            <input type="password" class="form-control" placeholder="Password" required="required" name="password">
        </div>
        <div class="form-group w-25 p-3" style="margin: auto;">
            <input type="hidden" name="command" value="auth">
            <button type="submit" class="btn btn-dark">Log in</button>
        </div>
    </form>
    <p style="color: red;" class="text-center">${requestScope.message}</p>
    <p class="text-center"><a href="/main?command=registration">Create an Account</a></p>
</div>
</body>
</html>
