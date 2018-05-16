package com.derivedmed.proj.command;

import com.derivedmed.proj.model.Role;
import com.derivedmed.proj.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyBonuses implements ICommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole()!= Role.SPEAKER){
            return "pages/403.jsp";
        }
        return "pages/mybonuses.jsp";
    }
}
