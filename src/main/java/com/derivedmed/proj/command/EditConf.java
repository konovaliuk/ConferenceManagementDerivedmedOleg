package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Conf;
import com.derivedmed.proj.model.Role;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.ConfService;
import com.derivedmed.proj.util.FieldChecker;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class EditConf implements Action {

    private final ConfService confService = ServiceFactory.getConfService();

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole() != Role.MODERATOR) {
            return "pages/403.jsp";
        }
        int confid = Integer.parseInt(req.getParameter("confid"));
        Conf conf = confService.getById(confid);
        req.setAttribute("conf", conf);
        if (req.getMethod().equals("GET")) {
            return "pages/editconf.jsp";
        }
        String confName = req.getParameter("confName").replaceAll("\\s{2,}", " ");
        String confPlace = req.getParameter("confPlace").replaceAll("\\s{2,}", " ");
        String confsDate = req.getParameter("confDate");
        if (!FieldChecker.checkField(confName) || !FieldChecker.checkField(confPlace)) {
            req.setAttribute("message", "fields may contains only letters and numbers");
            return "pages/editconf.jsp";
        }
        if (StringUtils.isNoneBlank(confName)) {
            conf.setName(confName);
        }
        if (StringUtils.isNoneBlank(confPlace)) {
            conf.setPlace(confPlace);
        }
        if (StringUtils.isNoneBlank(confsDate)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(confsDate, dateTimeFormatter);
            Timestamp confDate = Timestamp.valueOf(localDateTime);
            if (confDate.getTime() < new Date().getTime()) {
                req.setAttribute("message", "Please check date, it can`t be in the past.");
                return "pages/editconf.jsp";
            }
            conf.setDate(confDate);

        }
        confService.update(conf);
        req.setAttribute("confs", confService.getUpcoming(user));
        return new UpcomingConfs().execute(req, resp);
    }
}
