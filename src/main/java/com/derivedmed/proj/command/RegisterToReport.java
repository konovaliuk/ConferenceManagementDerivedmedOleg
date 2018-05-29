package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Conf;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class RegisterToReport implements Action {

    private final UserService userService =ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        int user_id = user.getId();
        int report_id = Integer.parseInt(req.getParameter("reportId"));
        if (userService.registerUserToReport(user_id, report_id)) {
            List<Conf> confs = ServiceFactory.getConfService().getUpcoming(user);
            Map<Integer, String> isUserRegisteredForReport = userService.isUserRegistered(user.getId(), confs);
            req.getSession().setAttribute("isRegistered", isUserRegisteredForReport);
            req.setAttribute("confs", confs);
            return "pages/upcoming.jsp";
        }
        return "pages/wrong.jsp";
    }
}
