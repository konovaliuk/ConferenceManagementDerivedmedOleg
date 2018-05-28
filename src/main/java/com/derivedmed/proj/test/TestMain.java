package com.derivedmed.proj.test;

import com.derivedmed.proj.dao.ConfDao;
import com.derivedmed.proj.dao.ReportDao;
import com.derivedmed.proj.dao.UserDao;
import com.derivedmed.proj.factory.DaoFactory;
import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.services.ConfService;
import com.derivedmed.proj.services.ReportService;
import com.derivedmed.proj.services.UserService;

public class TestMain {

    private static final ConfDao confDao = DaoFactory.getInstance().getConfDao();
    private static final UserDao userDao = DaoFactory.getInstance().getUserDao();
    private static final ReportDao reportDao = DaoFactory.getInstance().getReportDao();
    private static final ReportService reportService = ServiceFactory.getReportService();
    private static final UserService userService =ServiceFactory.getUserService();
    private static final ConfService confService =ServiceFactory.getConfService();

    public static void main(String[] args) {
//        User user = new User();
//        user.setEmail("speaker3@s.com");
//        user.setId(6);
//        user.setRole(Role.SPEAKER);
//        user.setRole_id(3);
//        user.setPassword("1");
//        user.setLogin("speaker three");
//        List<Conf> confs = confService.getUpcoming(user);
//        confs.forEach(System.out::println);
//        System.out.println(reportService.getByUserId(6));
//        System.out.println(userService.isUserRegistered(6, confs));
        System.out.println(userService.getUserByID(5));

    }

}


