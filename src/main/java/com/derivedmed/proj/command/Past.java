package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Conf;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.ConfService;
import com.derivedmed.proj.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class Past implements Action {

    private final UserService userService = ServiceFactory.getUserService();
    private final ConfService confService = ServiceFactory.getConfService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        List<Conf> confs = confService.getPast();
        Map<Integer, String> isUserRegisteredForReport = userService.isUserRegistered(user.getId(), confs);
        for (Map.Entry<Integer, String> entry : isUserRegisteredForReport.entrySet()) {
            if ("".equals(entry.getValue())) {
                entry.setValue("disabled");
            } else {
                entry.setValue("");
            }
        }
        Map<Integer, String> isUserVoted = userService.isUserVoted(user.getId(), confs);
        req.getSession().setAttribute("isVoted", isUserVoted);
        req.getSession().setAttribute("isRegistered", isUserRegisteredForReport);
        req.setAttribute("confs", confs);
        return "pages/past.jsp";
    }
}
