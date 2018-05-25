package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Authorization implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserService userService = ServiceFactory.getUserService();
        boolean isValid = userService.checkUser(login, password);
        if (isValid) {
            User user = userService.getByLogin(login);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("loc","en");
            return "pages/main.jsp";
        }
        req.setAttribute("message", "invalid email or password");
        return "pages/authorization.jsp";
    }
}
