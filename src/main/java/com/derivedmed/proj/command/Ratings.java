package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class Ratings implements Action {

    private final UserService userService =ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        List<User> speakers = userService.getSpeakersByRating();
        req.setAttribute("ratings",speakers);
        return "pages/ratings.jsp";
    }
}
