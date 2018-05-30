package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Conf;
import com.derivedmed.proj.model.Report;
import com.derivedmed.proj.model.Role;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.ConfService;
import com.derivedmed.proj.services.ReportService;
import com.derivedmed.proj.util.FieldChecker;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Add implements Action {

    private final ReportService reportService = ServiceFactory.getReportService();
    private final ConfService confService = ServiceFactory.getConfService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole() != Role.MODERATOR) {
            return "pages/403.jsp";
        }
        List<Conf> confs = confService.getUpcoming(user);
        req.setAttribute("confs", confs);
        if (req.getMethod().equals("GET")) {
            return "pages/cc.jsp";
        }
        if (req.getParameter("command").equals("addConf")) {
            return addConf(req,user);
        }
        return addReport(req);
    }

    private String addReport(HttpServletRequest req) {
        Report report = new Report();
        String confId = req.getParameter("confid");
        String reportName = req.getParameter("reportname");
        String reportDesc = req.getParameter("reportdesc");
        if (!FieldChecker.checkField(reportName) || !FieldChecker.checkField(reportDesc)) {
            req.setAttribute("reportMessage", "fields may contains only letters and numbers");
            return "pages/cc.jsp";
        }
        if (StringUtils.isNoneBlank(confId) && StringUtils.isNoneBlank(reportName) && StringUtils.isNoneBlank(reportDesc)) {
            report.setConf_id(Integer.parseInt(confId));
            report.setReportName(reportName);
            report.setReportDescription(reportDesc);
            int id = reportService.create(report);
            if (id == 0) {
                return "pages/wrong.jsp";
            }
        } else {
            req.setAttribute("reportMessage", "please fill all fields");
            return "pages/cc.jsp";
        }
        return "pages/cc.jsp";
    }

    private String addConf(HttpServletRequest req,User user){
        Conf conf = new Conf();
        String confName = req.getParameter("confName");
        String confPlace = req.getParameter("confPlace");
        String confsDate = req.getParameter("confDate");
        if (!FieldChecker.checkField(confName) || !FieldChecker.checkField(confPlace)) {
            req.setAttribute("message", "fields may contains only letters and numbers");
            return "pages/cc.jsp";
        }
        if (StringUtils.isNoneBlank(confName) && StringUtils.isNoneBlank(confPlace) && StringUtils.isNoneBlank(confsDate)) {
            conf.setName(confName);
            conf.setPlace(confPlace);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(confsDate, dateTimeFormatter);
            Timestamp confDate = Timestamp.valueOf(localDateTime);
            if (confDate.getTime() < new Date().getTime()) {
                req.setAttribute("message", "Please check date, it can`t be in the past.");
                return "pages/cc.jsp";
            }
            conf.setDate(confDate);
            confService.createConf(conf);
            req.setAttribute("confs", confService.getUpcoming(user));
            return "pages/cc.jsp";
        } else {
            req.setAttribute("message", "please fill all fields");
            return "pages/cc.jsp";
        }
    }
}
