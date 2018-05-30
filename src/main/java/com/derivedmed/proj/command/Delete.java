package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Role;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.ReportService;
import com.derivedmed.proj.services.UserService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Delete implements Action {

    private final ReportService reportService = ServiceFactory.getReportService();
    private final UserService userService = ServiceFactory.getUserService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole() == Role.SPEAKER || user.getRole() == Role.USER) {
            return "pages/403.jsp";
        }
        String reportId = req.getParameter("reportId");
        String userId = req.getParameter("userId");
        int report_id = 0;
        int user_id = 0;
        if (StringUtils.isNoneBlank(reportId) && checkInt(reportId)) {
            report_id = Integer.parseInt(reportId);
        }
        if (StringUtils.isNoneBlank(userId) && checkInt(userId)) {
            user_id = Integer.parseInt(userId);
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

        return "pages/wrong.jsp";
    }

    private boolean checkInt(String value) {
        Pattern p = Pattern.compile("[0-9]{1,}");
        Matcher m = p.matcher(value);
        return m.matches();
    }

}
