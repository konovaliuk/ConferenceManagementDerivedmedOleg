package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Report;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.ReportService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class MyConfs implements ICommand{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        ReportService reportService = ServiceFactory.getReportService();
        List<Report> reports = reportService.getByUserId(user.getId());
        req.setAttribute("reports", reports);
        return "pages/myconfs.jsp";
    }
}
