package com.derivedmed.proj.services;

import com.derivedmed.proj.model.Report;
import com.derivedmed.proj.model.ReportOfferedBySpeaker;
import com.derivedmed.proj.model.Role;

import java.util.List;

public interface ReportService extends Service {

    int create(Report report);

    Report getById(int id);

    boolean update(Report report);

    boolean delete(int id);

    boolean clearAll();

    List<Report> getAll();

    List<Report> getByUserId(int id);

    List<Integer> votedByUser(int user_id);

    boolean offerReport(int speakerId, int reportId, Role role);

    boolean confirmOffer(int userId, int reportId);

    boolean setReportToSpeaker(int speakerId, int reportId);

    boolean editReport(Report report,int id);

    List<Report> getReportsOfferedBySpeakerOrModer(int speakerid, boolean bySpeaker);

    List<ReportOfferedBySpeaker> offeredBySpeakers(boolean confirmed);
}
