package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Authorization implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserService userService = ServiceFactory.getUserService();
        boolean isValid = userService.checkUser(login, password);
        if (isValid) {
            User user = userService.getByLogin(login);
            req.getSession().setAttribute("user", user);
            return "pages/main.jsp";
        }
        req.setAttribute("message", "invalid username or password");
        return "pages/authorization.jsp";
    }
}
