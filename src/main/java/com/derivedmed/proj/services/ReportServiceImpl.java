package com.derivedmed.proj.services;

import com.derivedmed.proj.dao.ConfDao;
import com.derivedmed.proj.dao.ReportDao;
import com.derivedmed.proj.dao.UserDao;
import com.derivedmed.proj.factory.DaoFactory;
import com.derivedmed.proj.model.*;
import com.derivedmed.proj.util.annotations.Transactional;

import java.sql.Timestamp;
import java.util.List;

public class ReportServiceImpl implements ReportService {
    private static ReportServiceImpl instance = new ReportServiceImpl();
    private final UserDao userDao = DaoFactory.getInstance().getUserDao();
    private final ConfDao confDao = DaoFactory.getInstance().getConfDao();
    private final ReportDao reportDao = DaoFactory.getInstance().getReportDao();

    public static ReportServiceImpl getInstance() {
        return instance;
    }

    private ReportServiceImpl() {
    }

    @Override
    public int create(Report report) {
        return DaoFactory.getInstance().getReportDao().create(report);
    }

    @Override
    public Report getById(int id) {
        return DaoFactory.getInstance().getReportDao().getByID(id);
    }

    @Override
    public boolean update(Report report) {
        return DaoFactory.getInstance().getReportDao().update(report);
    }

    @Override
    public boolean delete(int id) {
        return DaoFactory.getInstance().getReportDao().delete(id);
    }

    @Override
    public boolean clearAll() {
        return DaoFactory.getInstance().getReportDao().clearAll();
    }

    @Override
    public List<Report> getAll() {
        List<Report> all = reportDao.getAll();
        return setSpeakersToReports(all);
    }

    private List<Report> setSpeakersToReports(List<Report> reports) {
        List<ReportWithSpeaker> withSpeakers = reportDao.getReportsWithSpeakers();
        for (ReportWithSpeaker withSpeaker : withSpeakers) {
            for (Report allReport : reports) {
                if (allReport.getId() == withSpeaker.getReportId()) {
                    allReport.setSpeakerName(withSpeaker.getSpeakerName());
                }
            }
        }
        return reports;
    }

    @Override
    public List<Report> getByUserId(int id) {
        List<Report> reports = setSpeakersToReports(reportDao.getReportsByUserId(id));
        reports = setConfNameAndDate(reports);
        return reports;
    }

    @Override
    public List<Integer> votedByUser(int user_id) {
        return reportDao.votedByUser(user_id);
    }

    @Override
    public boolean offerReport(int speakerId, int reportId, Role role) {
        boolean result = false;
        if (role == Role.MODERATOR) {
            result = reportDao.offerReport(speakerId, reportId, false);
        }
        if (role == Role.SPEAKER) {
            result = reportDao.offerReport(speakerId, reportId, true);
        }
        return result;
    }

    @Override
    public boolean confirmOffer(int userId, int reportId) {
        return reportDao.confirmOffer(userId, reportId);
    }

    @Override
    public synchronized boolean setReportToSpeaker(int speakerId, int reportId) {
        User user = userDao.getByID(speakerId);
        Report report = reportDao.getByID(reportId);
        Conf conf = confDao.getByID(report.getConf_id());
        Timestamp timestamp = conf.getDate();
        if (userDao.busySpeakersByDate(timestamp).contains(user)) {
            return false;
        }
        return reportDao.confirmOffer(speakerId, reportId);
    }

    @Override
    @Transactional
    public boolean editReport(Report report, int id) {
        update(report);
        if (id != 0) {
            return setReportToSpeaker(id, report.getId());
        }
        return true;
    }

    @Override
    public List<Report> getReportsOfferedBySpeakerOrModer(int speakerid, boolean bySpeaker) {
        List<Report> reports = setSpeakersToReports(reportDao.getReportsOfferedBySpeakerOrModer(speakerid, bySpeaker));
        reports = setConfNameAndDate(reports);
        return reports;
    }

    @Override
    public List<ReportOfferedBySpeaker> offeredBySpeakers(boolean confirmed) {
        return reportDao.reportsOfferedBySpeakers(confirmed);
    }

    @Transactional
    @Override
    public boolean deleteOfferedReport(int userId, int reportId) {
        return reportDao.deleteFromUsersReports(userId, reportId) && reportDao.delete(reportId);
    }

    @Transactional
    @Override
    public boolean deleteReport(int reportId) {
        return reportDao.deleteFromUsersReports(reportId) && reportDao.delete(reportId);
    }

    private List<Report> setConfNameAndDate(List<Report> reports) {
        List<ReportWithConf> reportWC = reportDao.getReportsWithConf();
        for (ReportWithConf reportWithConf : reportWC) {
            for (Report report : reports) {
                if (report.getId() == reportWithConf.getReportId()) {
                    report.setConfName(reportWithConf.getConfName());
                    report.setDate(reportWithConf.getConfDate());
                }
            }
        }
        return reports;
    }
}
