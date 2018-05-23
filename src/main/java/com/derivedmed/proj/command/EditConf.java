package com.derivedmed.proj.command;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Conf;
import com.derivedmed.proj.model.Role;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.services.ConfService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EditConf implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole()!= Role.MODERATOR){
            return "pages/403.jsp";
        }
        int confid = Integer.parseInt(req.getParameter("confid"));
        ConfService confService = ServiceFactory.getConfService();
        Conf conf = confService.getById(confid);
        req.setAttribute("conf", conf);
        if (req.getMethod().equals("GET")) {
            return "pages/editconf.jsp";
        }
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
        confService.update(conf);
        return new UpcomingConfs().execute(req, resp);
    }
}
