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

public class Vote implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService userService = ServiceFactory.getUserService();
        ConfService confService = ServiceFactory.getConfService();
        User user = (User) req.getSession().getAttribute("user");
        int rating = Integer.parseInt(req.getParameter("rating"));
        String reportId = req.getParameter("reportId");
        userService.vote(user.getId(),Integer.parseInt(reportId), rating);
        List<Conf> confs = confService.getPast();
        HashMap<Integer, String> isUserRegisteredForReport = userService.isUserRegistered(user.getId(), confs);
        isUserRegisteredForReport.entrySet().forEach(entry->{
            if ("".equals(entry.getValue())){
                entry.setValue("disabled");
            }else {
                entry.setValue("");
            }
        });
        HashMap<Integer, String> isUserVoted = userService.isUserVoted(user.getId(),confs);
        req.getSession().setAttribute("isVoted", isUserVoted);
        req.getSession().setAttribute("isRegistered", isUserRegisteredForReport);
        req.setAttribute("confs", confs);
        return "pages/past.jsp";
    }
}
