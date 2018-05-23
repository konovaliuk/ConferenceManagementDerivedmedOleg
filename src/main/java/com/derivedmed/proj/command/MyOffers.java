package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Report;
import com.derivedmed.proj.model.Role;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.ReportService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MyOffers implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole() == Role.USER) {
            return "pages/403.jsp";
        }
        ReportService reportService = ServiceFactory.getReportService();
        setAttributes(req, reportService, user);
        if (req.getMethod().equals("GET")) {
            return "pages/myoffers.jsp";
        }
        reportService.confirmOffer(user.getId(), Integer.parseInt(req.getParameter("reportid")));
        setAttributes(req, reportService, user);
        return "pages/myoffers.jsp";
    }

    private void setAttributes(HttpServletRequest req, ReportService reportService, User user) {
        List<Report> reports = reportService.getReportsOfferedBySpeakerOrModer(user.getId(), true);
        req.setAttribute("reports", reports);
        List<Report> reportsByModer = reportService.getReportsOfferedBySpeakerOrModer(user.getId(), false);
        req.setAttribute("reportsByModer", reportsByModer);
        List<Report> reportsBySpeakers = reportService.offeredBySpeakers();
        req.setAttribute("reportsBySpeakers", reportsBySpeakers);
    }

}
