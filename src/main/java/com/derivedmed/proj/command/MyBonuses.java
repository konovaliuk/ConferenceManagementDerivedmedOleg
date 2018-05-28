package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Role;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyBonuses implements Action {

    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole()!= Role.SPEAKER){
            return "pages/403.jsp";
        }
        user = userService.getUserByID(user.getId());
        req.setAttribute("user",user);
        System.out.println(user.getRole());
        return "pages/mybonuses.jsp";
    }
}
