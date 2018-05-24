package com.derivedmed.proj.services;

import com.derivedmed.proj.dao.ReportDao;
import com.derivedmed.proj.factory.DaoFactory;
import com.derivedmed.proj.model.MailData;

import java.sql.Timestamp;
import java.util.List;

public class NotificationServiceImpl implements NotificationService {
    private static NotificationServiceImpl ourInstance = new NotificationServiceImpl();
    private static ReportDao reportDao = DaoFactory.getInstance().getReportDao();

    public static NotificationServiceImpl getInstance() {
        return ourInstance;
    }

    private NotificationServiceImpl() {
    }

    @Override
    public List<MailData> dataForSheduler(Timestamp time) {
        return reportDao.getDataForNotifications(time);
    }
}
