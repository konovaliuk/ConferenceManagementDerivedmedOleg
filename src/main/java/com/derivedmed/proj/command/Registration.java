package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Role;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.UserService;
import com.derivedmed.proj.util.FieldChecker;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Registration implements Action {

    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = new User();
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (!FieldChecker.checkEmail(email) || StringUtils.isBlank(email)) {
            req.setAttribute("message", "invalid email");
            return "pages/registration.jsp";
        }
        if (!FieldChecker.checkUserName(username) || StringUtils.isBlank(username)) {
            req.setAttribute("message", "invalid login");
            return "pages/registration.jsp";
        }
        if (!FieldChecker.checkPassword(password) || StringUtils.isBlank(password)) {
            req.setAttribute("message", "invalid password");
            return "pages/registration.jsp";
        }
        user.setLogin(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(Role.USER);
        int id = userService.createUser(user);
        if (id != 0) {
            user.setId(id);
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("loc", "en");
            return "pages/main.jsp";
        }
        req.setAttribute("message", "sorry,current login or email already taken");
        return "pages/registration.jsp";
    }
}
