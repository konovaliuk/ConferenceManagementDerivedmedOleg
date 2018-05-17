package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Report;
import com.derivedmed.proj.model.Role;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.ConfService;
import com.derivedmed.proj.services.ReportService;
import com.derivedmed.proj.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;

public class EditReport implements ICommand {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole() != Role.MODERATOR) {
            return "pages/403.jsp";
        }
        ReportService reportService = ServiceFactory.getReportService();
        UserService userService = ServiceFactory.getUserService();
        ConfService confService = ServiceFactory.getConfService();
        int reportid = Integer.parseInt(req.getParameter("reportid"));
        Report report = reportService.getById(reportid);
        Timestamp time = confService.getById(report.getConf_id()).getDate();
        List<User> speakers = userService.getFreeThisDate(time);
        req.setAttribute("speakers", speakers);
        req.setAttribute("report", report);
        if (req.getMethod().equals("GET")) {
            return "pages/editreport.jsp";
        }
        String reportName = req.getParameter("reportName");
        if (!"".equals(reportName)) {
            report.setReport_name(reportName);
            reportService.update(report);
        }
        String speakerid = req.getParameter("speakerid");
        if (speakerid != null) {
            int id = Integer.parseInt(speakerid);
            reportService.setReportToSpeaker(id, report.getId());
        }
        return new UpcomingConfs().execute(req, resp);
    }
}
