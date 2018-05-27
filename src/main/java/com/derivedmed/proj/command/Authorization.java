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
        User user = userService.getByLogin(login);
        if (user == null|| !password.equals(user.getPassword())) {
            req.setAttribute("message", "invalid email or password");
            return "pages/authorization.jsp";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", user);
        session.setAttribute("loc", "en");
        return "pages/main.jsp";
    }
}
