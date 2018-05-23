package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Conf;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.ConfService;
import com.derivedmed.proj.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

public class UpcomingConfs implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        UserService userService = ServiceFactory.getUserService();
        ConfService confService = ServiceFactory.getConfService();
        List<Conf> confs = confService.getUpcoming(user);
        HashMap<Integer, String> isUserRegisteredForReport = userService.isUserRegistered(user.getId(), confs);
        req.getSession().setAttribute("isRegistered", isUserRegisteredForReport);
        req.setAttribute("confs", confs);
        return "pages/upoming.jsp";
    }
}
