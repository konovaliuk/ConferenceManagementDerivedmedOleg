package com.derivedmed.proj.test;

import com.derivedmed.proj.dao.ConfDao;
import com.derivedmed.proj.dao.ReportDao;
import com.derivedmed.proj.dao.UserDao;
import com.derivedmed.proj.factory.DaoFactory;
import com.derivedmed.proj.notification.scheduler.SchedulerStarter;

import java.sql.Timestamp;
import java.util.Date;

public class TestMain {

    private static final ConfDao confDao = DaoFactory.getInstance().getConfDao();
    private static final UserDao userDao = DaoFactory.getInstance().getUserDao();
    private static final ReportDao reportDao = DaoFactory.getInstance().getReportDao();

    public static void main(String[] args) {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        reportDao.getDataForNotifications(timestamp).forEach(System.out::println);

        SchedulerStarter.startSheduler();

    }

}


