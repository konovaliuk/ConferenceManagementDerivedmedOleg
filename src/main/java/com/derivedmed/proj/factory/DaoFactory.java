package com.derivedmed.proj.factory;

import com.derivedmed.proj.dao.ConfDao;
import com.derivedmed.proj.dao.ReportDao;
import com.derivedmed.proj.dao.UserDao;

public class DaoFactory {
    private static final UserDao userDao = new UserDao();
    private static final ConfDao confDao = new ConfDao();
    private static final ReportDao reportDao = new ReportDao();

    private static final DaoFactory ourInstance = new DaoFactory();

    public static DaoFactory getInstance() {
        return ourInstance;
    }

    private DaoFactory() {
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public ConfDao getConfDao() {
        return confDao;
    }

    public ReportDao getReportDao() {
        return reportDao;
    }
}
