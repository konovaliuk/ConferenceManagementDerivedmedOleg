package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.ReportOfferedBySpeaker;
import com.derivedmed.proj.model.Role;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.ConfService;
import com.derivedmed.proj.services.ReportService;
import com.derivedmed.proj.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;

public class OffersControl implements Action {

    private final ReportService reportService = ServiceFactory.getReportService();
    private final ConfService confService = ServiceFactory.getConfService();
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole() != Role.MODERATOR) {
            return "pages/403.jsp";
        }
        List<ReportOfferedBySpeaker> reports = reportService.offeredBySpeakers(false);
        List<ReportOfferedBySpeaker> confirmed = reportService.offeredBySpeakers(true);
        req.setAttribute("reports", reports);
        req.setAttribute("confirmed", confirmed);
        if (req.getMethod().equals("GET")) {
            return "pages/offersControl.jsp";
        }
        Timestamp time = confService.getById(Integer.parseInt(req.getParameter("confId"))).getDate();
        List<User> freeSpeakers = userService.getFreeThisDate(time);
        int userId = Integer.parseInt(req.getParameter("userId"));
        int reportId = Integer.parseInt(req.getParameter("reportId"));
        long count = freeSpeakers.stream()
                .filter(u -> u.getId() == userId)
                .count();
        if (count == 0) {
            return "pages/offersControl.jsp";
        }
        reportService.confirmOffer(userId, reportId);
        req.setAttribute("reports", reportService.offeredBySpeakers(false));
        req.setAttribute("confirmed", reportService.offeredBySpeakers(true));
        return "pages/offersControl.jsp";
    }
}
