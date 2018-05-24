package com.derivedmed.proj.notification.scheduler;

import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.MailData;
import com.derivedmed.proj.notification.MailSender;
import com.derivedmed.proj.services.NotificationService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class SchedulerTask implements Job {
    private static NotificationService notificationService = ServiceFactory.getNotificationService();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Timestamp time = new Timestamp(new Date().getTime());
        List<MailData> mailData = notificationService.dataForSheduler(time);
        mailData.forEach(m -> MailSender.getInstance().sendMail(m));
    }
}
