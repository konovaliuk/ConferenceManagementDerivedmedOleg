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
import java.util.List;

public class OfferBySpeaker implements Action {

    private final ReportService reportService =ServiceFactory.getReportService();
    private final ConfService confService =ServiceFactory.getConfService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole()!= Role.SPEAKER){
            return "pages/403.jsp";
        }
        List<Conf> confs = confService.getUpcoming(user);
        req.setAttribute("confs",confs);
        if (req.getMethod().equals("GET")){
            return "pages/offer.jsp";
        }
        Report report = new Report();
        report.setConf_id(Integer.parseInt(req.getParameter("confid")));
        report.setReport_name(req.getParameter("reportname"));
        report.setReport_description(req.getParameter("reportdesc"));
        int id = reportService.create(report);
        if (id == 0){
            return "pages/wrong.jsp";
        }
        reportService.offerReport(user.getId(),id,user.getRole());
        return "pages/offer.jsp";
    }
}
