package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Role;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.ReportService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Delete implements Action {

    private final ReportService reportService = ServiceFactory.getReportService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole() == Role.SPEAKER || user.getRole() == Role.USER) {
            return "pages/403.jsp";
        }
        int report_id = Integer.parseInt(req.getParameter("reportId"));
        if ("deleteOfferedReport".equals(req.getParameter("command"))) {
            int user_id = Integer.parseInt(req.getParameter("userId"));
            reportService.deleteOfferedReport(user_id, report_id);
            return new OffersControl().execute(req, resp);
        }
        if ("deleteReport".equals(req.getParameter("command"))) {
            reportService.deleteReport(report_id);
            return new UpcomingConfs().execute(req, resp);
        }

        return "pages/wrong.jsp";
    }

}
