package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Role;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.ConfService;
import com.derivedmed.proj.services.ReportService;
import com.derivedmed.proj.services.UserService;
import com.derivedmed.proj.util.FieldChecker;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Delete implements Action {

    private final ReportService reportService = ServiceFactory.getReportService();
    private final UserService userService = ServiceFactory.getUserService();
    private final ConfService confService = ServiceFactory.getConfService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole() == Role.SPEAKER || user.getRole() == Role.USER) {
            return "pages/403.jsp";
        }
        String reportId = req.getParameter("reportId");
        String userId = req.getParameter("userId");
        String confId = req.getParameter("confId");
        int report_id = 0;
        int user_id = 0;
        int conf_id = 0;
        if (StringUtils.isNoneBlank(reportId) && FieldChecker.checkInt(reportId)) {
            report_id = Integer.parseInt(reportId);
        }
        if (StringUtils.isNoneBlank(userId) && FieldChecker.checkInt(userId)) {
            user_id = Integer.parseInt(userId);
        }
        if (StringUtils.isNoneBlank(confId) && FieldChecker.checkInt(confId)) {
            conf_id = Integer.parseInt(confId);
        }
        if ("deleteOfferedReport".equals(req.getParameter("command")) && report_id != 0 && user_id != 0) {
            reportService.deleteOfferedReport(user_id, report_id);
            return new OffersControl().execute(req, resp);
        }
        if ("deleteReport".equals(req.getParameter("command")) && report_id != 0) {
            reportService.deleteReport(report_id);
            return new UpcomingConfs().execute(req, resp);
        }
        if ("deleteUser".equals(req.getParameter("command")) && user_id != 0) {
            userService.delete(user_id);
            return new EditUsers().execute(req, resp);
        }
        if ("deleteConf".equals(req.getParameter("command")) && conf_id != 0) {
            confService.delete(conf_id);
            return new UpcomingConfs().execute(req, resp);
        }

        return "pages/wrong.jsp";
    }


}
