package com.derivedmed.proj.dao;

import com.derivedmed.proj.model.*;
import com.derivedmed.proj.util.PreparedStatmentCompilier;
import com.derivedmed.proj.util.rsparser.ResultSetParser;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDao implements CrudDao<Report> {

    private static Logger LOGGER = LogManager.getLogger(ReportDao.class);
    private final ResultSetParser resultSetParser = ResultSetParser.getInstance();
    private final TransactionManager transactionManager = TransactionManager.getInstance();
    private static final String SQL_EXCEPTION = "SQL exception Report DAO";
    private static final String createSql = "insert into reports (conf_id, report_name, report_desk) values (?, ?, ?)";
    private static final String getByIdSql = "select * from reports where report_id = ?";
    private static final String updateSql = "update reports set conf_id = ?, report_name = ?, report_desk = ? where report_id = ?";
    private static final String deleteSql = "delete from reports where report_id = ?";
    private static final String getAllSql = "select * from reports";
    private static final String clearAllSql = "delete from reports";
    private static final String getReportsByUserIdSql = "select r.report_id, r.conf_id, report_name, report_desk from reports r " +
            "join users_reports ur on r.report_id = ur.report_id " +
            "where ur.user_id = ?";
    private static final String confirmOfferSecondSql = "update users_reports set active_speaker = ?, confirmed = ?, by_speaker = ?, by_moder = ? where report_id = ? and user_id <> ?";
    private static final String offeredBySpeakerSql = "select reports.report_id,conf_id,report_name,report_desk from reports " +
            "join users_reports on reports.report_id = users_reports.report_id where user_id =? and by_speaker =? and by_moder =?";
    private static final String votedByUserSql = "select report_id from users_reports where user_id =? and rating > 0";
    private static final String reportsOfferedBySpeakersSql = "select c.conf_id, confirmed, ur.user_id, ur.report_id, u.login, r.report_name, c.conf_name, c.confDate from users u " +
            "join users_reports ur on u.user_id = ur.user_id " +
            "join reports r on ur.report_id = r.report_id " +
            "join confs c on r.conf_id = c.conf_id " +
            "where u.role_id = ? and by_speaker = ? and confirmed = ? and active_speaker = ? ";
    private static final String dataForNotificationsSql = "select r.report_name, u.email, u.login, c.conf_name, c.conf_place, c.confDate from users u " +
            "join users_reports ur on u.user_id = ur.user_id " +
            "join reports r on ur.report_id = r.report_id " +
            "join confs c on r.conf_id = c.conf_id where c.confDate > ? and c.confDate < ? group by u.email";
    private static final String setSpeakersToReportsSql = "select login from users u " +
            "join users_reports ur on u.user_id = ur.user_id where report_id = ? and active_speaker = ?";
    private static final String isSpeakerExistSql = "select* from users_reports where user_id =? and report_id=?";
    private static final String updateOrInsertSql = "select * from users_reports where user_id = ? and report_id = ?";
    private static final String deleteFromUsersReportsSql = " delete from users_reports where user_id = ? and report_id = ?";
    private static final String deleteReportFromUsersReportsSq = "delete from users_reports where report_id = ?";
    private static final String getReportsWithConfSql = " select conf_name, confDate, report_id from reports r " +
            "join confs c on r.conf_id = c.conf_id ";
    private static final String getReportsWithSpeakersSql = " select login, report_id from users u " +
            "join users_reports ur on u.user_id = ur.user_id " +
            "where active_speaker = ?";
    private static final String confirmOfferUpdate = "update users_reports set active_speaker = ?, confirmed = ? where user_id = ? and report_id = ?";
    private static final String confirmOfferInsert = "insert into users_reports (active_speaker, confirmed, user_id, report_id) values(?, ?, ?, ?)";
    private static final String updateOrInsertInsertSql = "insert into users_reports (active_speaker, by_speaker, by_moder, confirmed, user_id, report_id) values(?,?,?,?,?,?)";
    private static final String updateOrInsertUpdateSql = "update users_reports set active_speaker = ?, by_speaker = ?, by_moder = ?, confirmed = ? where user_id = ? and report_id = ?";


    @Override
    public int create(Report report) {
        int autoGeneratedId;
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connectionProxy
                     .prepareStatement(createSql, Statement.RETURN_GENERATED_KEYS), new Object[]{report.getConf_id(), report.getReport_name(), report.getReport_description()})) {
            preparedStatement.executeUpdate();
            ResultSet id = preparedStatement.getGeneratedKeys();
            id.next();
            autoGeneratedId = id.getInt(1);
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
            return 0;
        }
        return autoGeneratedId;
    }

    @Override
    public Report getByID(int id) {
        Report result = new Report();
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connectionProxy.prepareStatement(getByIdSql), new Object[]{id})) {
            List<Report> report = resultSetParser.parse(preparedStatement.executeQuery(), new Report());
            if (!report.isEmpty()) {
                result = report.get(0);
            }
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return result;
    }

    @Override
    public boolean update(Report report) {
        boolean result = true;
        try (ConnectionProxy connection = transactionManager.getConnection();
             PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connection.prepareStatement(updateSql),
                     new Object[]{report.getConf_id(), report.getReport_name(), report.getReport_name(), report.getId()})) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
            result = false;
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = true;
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connectionProxy.prepareStatement(deleteSql), new Object[]{id})) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
            result = false;
        }
        return result;
    }

    @Override
    public List<Report> getAll() {
        List<Report> resultList = new ArrayList<>();
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(getAllSql)) {
            resultList = resultSetParser.parse(preparedStatement.executeQuery(), new Report());
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return resultList;
    }

    @Override
    public boolean clearAll() {
        boolean result = true;
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             Statement statement = connectionProxy.createStatement()) {
            statement.executeUpdate(clearAllSql);
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
            result = false;
        }
        return result;
    }
    public List<ReportWithConf> getReportsWithConf(){
        List<ReportWithConf> reports = new ArrayList<>();
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(getReportsWithConfSql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            reports = resultSetParser.parse(resultSet, new ReportWithConf());

        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return reports;
    }
    public boolean deleteFromUsersReports(int userId, int reportId){
        boolean result = false;
        try(ConnectionProxy connectionProxy = transactionManager.getConnection();
        PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connectionProxy
                .prepareStatement(deleteFromUsersReportsSql),new Object[]{userId,reportId})) {
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return result;
    }
    public boolean deleteFromUsersReports(int reportId){
        boolean result = false;
        try(ConnectionProxy connectionProxy = transactionManager.getConnection();
            PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connectionProxy
                    .prepareStatement(deleteReportFromUsersReportsSq),new Object[]{reportId})) {
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return result;
    }

    public List<ReportWithSpeaker> getReportsWithSpeakers() {
        List<ReportWithSpeaker> reports = new ArrayList<>();
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connectionProxy.prepareStatement(getReportsWithSpeakersSql), new Object[]{true})) {
            ResultSet resultSet = preparedStatement.executeQuery();
            reports = resultSetParser.parse(resultSet, new ReportWithSpeaker());
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return reports;
    }

    public List<Report> getReportsByUserId(int id) {
        List<Report> reports = new ArrayList<>();
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connectionProxy.prepareStatement(getReportsByUserIdSql), new Object[]{id})) {
            reports = resultSetParser.parse(preparedStatement.executeQuery(), new Report());
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return reports;
    }

    public boolean offerReport(int speakerId, int reportId, boolean bySpeaker) {
        boolean result = true;
        try (ConnectionProxy connectionProxy = transactionManager.getConnection()) {
            String offerReportSql = updateOrInsert(connectionProxy, speakerId, reportId);
            PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connectionProxy.prepareStatement(offerReportSql),
                    new Object[]{false, bySpeaker, !bySpeaker, false, speakerId, reportId});
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
            result = false;
        }
        return result;
    }

    public boolean confirmOffer(int userId, int reportId) {
        boolean result = true;
        String sql;
        if (isSpeakerReportExist(userId, reportId)) {
            sql = confirmOfferUpdate;
        } else {
            sql = confirmOfferInsert;
        }
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connectionProxy.prepareStatement(sql),
                     new Object[]{true, true, userId, reportId});
             PreparedStatement preparedStatement1 = PreparedStatmentCompilier.setValues(connectionProxy.prepareStatement(confirmOfferSecondSql),
                     new Object[]{false, false, false, false, reportId, userId})) {
            preparedStatement.executeUpdate();
            preparedStatement1.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
            result = false;
        }
        return result;
    }

    public List<Report> getReportsOfferedBySpeakerOrModer(int userid, boolean bySpeaker) {
        List<Report> reports = new ArrayList<>();
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connectionProxy.prepareStatement(offeredBySpeakerSql),
                     new Object[]{userid, bySpeaker, !bySpeaker})) {
            reports = resultSetParser.parse(preparedStatement.executeQuery(), new Report());
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return reports;
    }


    public List<Integer> votedByUser(int user_id) {
        List<Integer> reports = new ArrayList<>();
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(votedByUserSql)) {
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                reports.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return reports;
    }

    public List<ReportOfferedBySpeaker> reportsOfferedBySpeakers(boolean confirmed) {

        List<ReportOfferedBySpeaker> reportOfferedBySpeakers = new ArrayList<>();
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connectionProxy.prepareStatement(reportsOfferedBySpeakersSql), new Object[]{3, true, confirmed, confirmed})) {
            reportOfferedBySpeakers = resultSetParser.parse(preparedStatement.executeQuery(), new ReportOfferedBySpeaker());
            if (!reportOfferedBySpeakers.isEmpty()) {
                return reportOfferedBySpeakers;
            }
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return reportOfferedBySpeakers;
    }

    public List<MailData> getDataForNotifications(Timestamp time) {
        List<MailData> result = new ArrayList<>();
        long oneDayMillis = DateUtils.MILLIS_PER_DAY;
        Timestamp nextDay = new Timestamp(time.getTime() + oneDayMillis);
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connectionProxy.prepareStatement(dataForNotificationsSql), new Object[]{time, nextDay})) {
            result = resultSetParser.parse(preparedStatement.executeQuery(), new MailData());
            if (!result.isEmpty()) {
                return result;
            }

        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return result;
    }

    private boolean isSpeakerReportExist(int userid, int reportid) {
        boolean exist = false;
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connectionProxy.prepareStatement(isSpeakerExistSql), new Object[]{userid, reportid})) {
            ResultSet resultSet = preparedStatement.executeQuery();
            exist = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }

    private String updateOrInsert(ConnectionProxy connectionProxy, int speakerId, int reportId) {
        try (PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connectionProxy.prepareStatement(updateOrInsertSql), new Object[]{speakerId, reportId});
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.next()) {
                return updateOrInsertInsertSql;
            }
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return updateOrInsertUpdateSql;
    }
}
