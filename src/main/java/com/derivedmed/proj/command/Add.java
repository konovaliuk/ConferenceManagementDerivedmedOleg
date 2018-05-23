package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Conf;
import com.derivedmed.proj.model.Report;
import com.derivedmed.proj.model.Role;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.ConfService;
import com.derivedmed.proj.services.ReportService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Add implements Action {

    private final ReportService reportService = ServiceFactory.getReportService();
    private final ConfService confService = ServiceFactory.getConfService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole()!= Role.MODERATOR){
            return "pages/403.jsp";
        }
        List<Conf> confs = confService.getUpcoming(user);
        req.setAttribute("confs",confs);
        if (req.getMethod().equals("GET")){
            return "pages/cc.jsp";
        }
        if (req.getParameter("command").equals("addConf")){
            Conf conf = new Conf();
            String confName = req.getParameter("confName");
            String confPlace = req.getParameter("confPlace");
            String confsDate = req.getParameter("confDate");
            if (!"".equals(confName)) {
                conf.setName(confName);
            }
            if (!"".equals(confPlace)) {
                conf.setPlace(confPlace);
            }
            if (!"".equals(confsDate)) {
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime localDateTime = LocalDateTime.parse(confsDate, dateTimeFormatter);
                Timestamp confDate = Timestamp.valueOf(localDateTime);
                conf.setDate(confDate);
            }
            confService.createConf(conf);
            req.setAttribute("confs",confService.getUpcoming(user));
            return "pages/cc.jsp";
        }
        Report report = new Report();
        report.setConf_id(Integer.parseInt(req.getParameter("confid")));
        report.setReport_name(req.getParameter("reportname"));
        report.setReport_description(req.getParameter("reportdesc"));
        int id = reportService.create(report);
        if (id == 0){
            return "pages/wrong.jsp";
        }
        return "pages/cc.jsp";
    }
}
