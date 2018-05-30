package com.derivedmed.proj.notification.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerStarter {
    private static SchedulerStarter ourInstance = new SchedulerStarter();
    private static Logger LOGGER = LogManager.getLogger(SchedulerStarter.class);
    private static Scheduler scheduler;

    public static SchedulerStarter getInstance() {
        return ourInstance;
    }

    private SchedulerStarter() {
    }

    public static void startSheduler() {
        JobDetail jobDetail = JobBuilder.newJob(SchedulerTask.class).build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("Send mails")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 30 16 * * ?"))
                .build();
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            LOGGER.error("exc",e);
        }
    }

    public static void stop(){
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            LOGGER.error(e);
        }
    }
}
