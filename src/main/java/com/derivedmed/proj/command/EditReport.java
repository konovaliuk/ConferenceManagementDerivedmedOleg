package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Report;
import com.derivedmed.proj.model.Role;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.ConfService;
import com.derivedmed.proj.services.ReportService;
import com.derivedmed.proj.services.UserService;
import com.derivedmed.proj.util.FieldChecker;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;

public class EditReport implements Action {

    private final ReportService reportService = ServiceFactory.getReportService();
    private final UserService userService = ServiceFactory.getUserService();
    private final ConfService confService = ServiceFactory.getConfService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole() != Role.MODERATOR) {
            return "pages/403.jsp";
        }
        int reportid = Integer.parseInt(req.getParameter("reportid"));
        Report report = reportService.getById(reportid);
        Timestamp time = confService.getById(report.getConf_id()).getDate();
        List<User> speakers = userService.getFreeThisDate(time);
        req.setAttribute("speakers", speakers);
        req.setAttribute("report", report);
        if (req.getMethod().equals("GET")) {
            return "pages/editreport.jsp";
        }
        String reportName = req.getParameter("reportName").replaceAll("\\s{2,}", " ");
        String speakerid = req.getParameter("speakerid");
        int id = 0;
        if (StringUtils.isNoneBlank(speakerid)) {
            id = Integer.parseInt(speakerid);
        }
        if (FieldChecker.checkField(reportName)&&StringUtils.isNoneBlank(reportName)) {
            report.setReportName(reportName);
        }
        reportService.editReport(report, id);
        return new UpcomingConfs().execute(req, resp);
    }

}
