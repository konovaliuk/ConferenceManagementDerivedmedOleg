package com.derivedmed.proj.services;

import com.derivedmed.proj.model.MailData;

import java.sql.Timestamp;
import java.util.List;

public interface NotificationService extends Service {
    List<MailData> dataForScheduler(Timestamp time);
}
