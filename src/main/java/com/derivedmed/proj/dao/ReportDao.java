package com.derivedmed.proj.dao;

import com.derivedmed.proj.model.Report;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.util.QueryGenerator;
import com.derivedmed.proj.util.rsparser.ResultSetParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportDao implements CrudDao<Report> {

    private static Logger LOGGER = LogManager.getLogger(ReportDao.class);
    private final String SQL_EXCEPTION = "SQL exception Report DAO";


    @Override
    public int create(Report report) {
        int autoGeneratedId = 0;
        String CREATE_SQL = "insert into reports (conf_id,report_name,report_desk) values(?,?,?)";
        try (ConnectionProxy connectionProxy = TransactionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connectionProxy
                     .prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, report.getConf_id());
            preparedStatement.setString(2, report.getReport_name());
            preparedStatement.setString(3, report.getReport_description());
            preparedStatement.executeUpdate();
            ResultSet id = preparedStatement.getGeneratedKeys();
            id.next();
            autoGeneratedId = id.getInt(1);
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return autoGeneratedId;
    }

    @Override
    public Report getByID(int id) {
        Report report = new Report();
        String GET_BY_ID_SQL = "SELECT * from reports where report_id = ?";
        try (ConnectionProxy connectionProxy = TransactionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(GET_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            report = ResultSetParser.getInstance().parse(preparedStatement.executeQuery(), new Report()).get(0);
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return report;
    }

    @Override
    public boolean update(Report report) {
        String UPDATE_SQL = "UPDATE reports SET conf_id = ?, report_name = ?, report_desk = ? WHERE report_id = ?";
        try (ConnectionProxy connection = TransactionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setInt(1, report.getConf_id());
            preparedStatement.setString(2, report.getReport_name());
            preparedStatement.setString(3, report.getReport_description());
            preparedStatement.setInt(4, report.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        String DELETE_SQL = "delete from reports where report_id =?";
        try (ConnectionProxy connectionProxy = TransactionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(DELETE_SQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
            return false;
        }
        return true;
    }

    @Override
    public List<Report> getAll() {
        List<Report> resultList = new ArrayList<>();
        String GET_ALL_SQL = "select * from reports";
        try (ConnectionProxy connectionProxy = TransactionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(GET_ALL_SQL)) {
            resultList = ResultSetParser.getInstance().parse(preparedStatement.executeQuery(), new Report());
            resultList = setSpeakersToReport(connectionProxy, resultList);
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return resultList;
    }

    public List<Report> getAllConfirmed() {
        List<Report> confirmed = new ArrayList<>();
        String get_all_confirmed = "select r.* from reports r join users_reports ur on r.report_id = ur.report_id where confirmed = ?";
        try (ConnectionProxy connectionProxy = TransactionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(get_all_confirmed)) {
            preparedStatement.setBoolean(1, true);
            confirmed = ResultSetParser.getInstance().parse(preparedStatement.executeQuery(), new Report());
            confirmed = setSpeakersToReport(connectionProxy, confirmed);
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return confirmed;
    }

    @Override
    public boolean clearAll() {
        try (ConnectionProxy connectionProxy = TransactionManager.getInstance().getConnection();
             Statement statement = connectionProxy.createStatement()) {
            String CLEAR_ALL_SQL = "DELETE FROM reports";
            statement.executeUpdate(CLEAR_ALL_SQL);
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
            return false;
        }
        return true;
    }

    public List<Report> getReportsByUserId(int id) {
        List<Report> reports = new ArrayList<>();
        String GET_REPORTS_BY_USER_ID_SQL = "SELECT reports.report_id,conf_id, report_name,report_desk FROM reports JOIN users_reports u ON reports.report_id = u.report_id WHERE u.user_id = ?;";
        try (ConnectionProxy connectionProxy = TransactionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(GET_REPORTS_BY_USER_ID_SQL)) {
            preparedStatement.setInt(1, id);
            reports = ResultSetParser.getInstance().parse(preparedStatement.executeQuery(), new Report());
            reports = setSpeakersToReport(connectionProxy, reports);
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return reports;
    }

    public List<Report> getPastReports() {
        ArrayList<Report> reports = new ArrayList<>();
        String GET_PAST_REPORTS_SQL = "SELECT reports.report_id,conf_id, report_name,report_desk FROM reports JOIN confs c ON reports.conf_id = c.conf_id WHERE c.conf_date < ?;";
        try (ConnectionProxy connectionProxy = TransactionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(GET_PAST_REPORTS_SQL)) {
            preparedStatement.setTimestamp(1, new Timestamp(new Date().getTime()));
            reports = ResultSetParser.getInstance().parse(preparedStatement.executeQuery(), new Report());
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return reports;
    }

    public List<Report> getUpcomingReports() {
        ArrayList<Report> reports = new ArrayList<>();
        String GET_UPCOMING_REPORTS_SQL = "SELECT reports.report_id,conf_id, report_name,report_desk FROM reports JOIN confs c ON reports.conf_id = c.conf_id WHERE c.conf_date > ?;";
        try (ConnectionProxy connectionProxy = TransactionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(GET_UPCOMING_REPORTS_SQL)) {
            preparedStatement.setTimestamp(1, new Timestamp(new Date().getTime()));
            reports = ResultSetParser.getInstance().parse(preparedStatement.executeQuery(), new Report());
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return reports;
    }

    public boolean offerReport(int speakerId, int reportId, boolean bySpeaker) {
        try (ConnectionProxy connectionProxy = TransactionManager.getInstance().getConnection()) {
            String sql = updateOrInsert(connectionProxy, speakerId, reportId);
            PreparedStatement preparedStatement = connectionProxy.prepareStatement(sql);
            preparedStatement.setBoolean(1, false);
            preparedStatement.setBoolean(2, bySpeaker);
            preparedStatement.setBoolean(3, !bySpeaker);
            preparedStatement.setBoolean(4, false);
            preparedStatement.setInt(5, speakerId);
            preparedStatement.setInt(6, reportId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
            return false;
        }
        return true;
    }

    private String updateOrInsert(ConnectionProxy connectionProxy, int speakerId, int reportId) {
        QueryGenerator queryGenerator = new QueryGenerator();
        String result = "";
        String sql = queryGenerator.select("*")
                .from("users_reports")
                .where("user_id")
                .and("report_id")
                .generate();
        try (PreparedStatement preparedStatement = queryGenerator.setValues(connectionProxy.prepareStatement(sql), new Object[]{speakerId, reportId});
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (!resultSet.next()) {
                result = new QueryGenerator()
                        .insert("users_reports", new String[]{"active_speaker", "by_speaker", "by_moder", "confirmed", "user_id", "report_id"})
                        .generate();
            } else {
                result = new QueryGenerator()
                        .update("users_reports")
                        .set(new String[]{"active_speaker", "by_speaker", "by_moder", "confirmed"})
                        .where("user_id")
                        .and("report_id")
                        .generate();
            }

        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return result;
    }

    public boolean confirmOffer(int userId, int reportId) {
        QueryGenerator queryGenerator = new QueryGenerator();
        String sql;
        if (isSpeakerReportExist(userId, reportId)) {
            sql = queryGenerator.update("users_reports")
                    .set(new String[]{"active_speaker", "confirmed"})
                    .where("user_id")
                    .and("report_id")
                    .generate();
        } else {
            sql = "insert into users_reports (active_speaker, confirmed, user_id, report_id)  values(?,?,?,?)";
        }
        try (ConnectionProxy connectionProxy = TransactionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, true);
            preparedStatement.setBoolean(2, true);
            preparedStatement.setInt(3, userId);
            preparedStatement.setInt(4, reportId);
            preparedStatement.executeUpdate();
            PreparedStatement preparedStatement1 = connectionProxy.prepareStatement("update users_reports set by_moder = ?, by_speaker = ? where report_id = ? and user_id <> ?");
            preparedStatement1.setBoolean(1, false);
            preparedStatement1.setBoolean(2, false);
            preparedStatement1.setInt(3, reportId);
            preparedStatement1.setInt(4, userId);
            preparedStatement1.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
            return false;
        }
        return true;
    }

    private boolean isSpeakerReportExist(int userid, int reportid) {
        boolean exist = false;
        try (ConnectionProxy connectionProxy = TransactionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement("select* from users_reports where user_id =? and report_id=?")) {
            preparedStatement.setInt(1, userid);
            preparedStatement.setInt(2, reportid);
            ResultSet resultSet = preparedStatement.executeQuery();
            exist = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exist;
    }


    public List<Report> getByConf(int id) {
        List<Report> result = new ArrayList<>();
        String GET_BY_CONF = "select * from reports where conf_id =? and confirmed = ?";
        try (ConnectionProxy connectionProxy = TransactionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(GET_BY_CONF)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setBoolean(2, true);
            result = ResultSetParser.getInstance().parse(preparedStatement.executeQuery(), new Report());
            result = setSpeakersToReport(connectionProxy, result);
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return result;
    }

    public List<Report> getReportsWithSpeakers(int confId) {
        List<Report> result = new ArrayList<>();
        try (ConnectionProxy connectionProxy = TransactionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement("select report_id,conf_id,report_name,report_desk from reports join users_reports on reports.report_id = users_reports.report_id where conf_id =? and active_speaker =?");) {
            preparedStatement.setInt(1, confId);
            preparedStatement.setBoolean(2, true);
            result = ResultSetParser.getInstance().parse(preparedStatement.executeQuery(), new Report());
            result = setSpeakersToReport(connectionProxy, result);
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return result;
    }

    public List<Report> getReportsOfferedBySpeakerOrModer(int userid, boolean bySpeaker) {
        String OFFERED_BY_SPEAKER_SQL = "select reports.report_id,conf_id,report_name,report_desk from reports join users_reports on reports.report_id = users_reports.report_id where user_id =? and by_speaker =? and by_moder =?";
        List<Report> reports = new ArrayList<>();
        try (ConnectionProxy connectionProxy = TransactionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(OFFERED_BY_SPEAKER_SQL)) {
            preparedStatement.setInt(1, userid);
            preparedStatement.setBoolean(2, bySpeaker);
            preparedStatement.setBoolean(3, !bySpeaker);
            reports = ResultSetParser.getInstance().parse(preparedStatement.executeQuery(), new Report());
            reports = setSpeakersToReport(connectionProxy, reports);
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return reports;
    }


    public List<Integer> votedByUser(int user_id) {
        List<Integer> reports = new ArrayList<>();
        try (ConnectionProxy connectionProxy = TransactionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement("select report_id from users_reports where user_id =? and rating > 0")) {
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

    private List<Report> setSpeakersToReport(ConnectionProxy connectionProxy, List<Report> result) {
        try (PreparedStatement preparedStatement = connectionProxy.prepareStatement("select login from users u join users_reports ur on u.user_id = ur.user_id where report_id = ? and active_speaker = ?")) {
            for (Report report : result) {
                preparedStatement.setInt(1, report.getId());
                preparedStatement.setBoolean(2, true);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    report.setSpeakerName(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return result;
    }

    public List<Report> reportsOfferedBySpeakers() {
        Map<Report, User> reportsFromSpeakers = new HashMap<>();
        List<Report> reports = new ArrayList<>();
        QueryGenerator queryGenerator = new QueryGenerator();
        String sql = queryGenerator.select("reports.*")
                .from("reports")
                .join("users_reports", "reports.report_id = users_reports.report_id")
                .where("active_speaker")
                .and("confirmed")
                .and("by_speaker")
                .generate();
        try (ConnectionProxy connectionProxy = TransactionManager.getInstance().getConnection();
             PreparedStatement preparedStatement = queryGenerator.setValues(connectionProxy
                     .prepareStatement(sql), new Object[]{false, false, true})) {
            reports = ResultSetParser.getInstance().parse(preparedStatement.executeQuery(), new Report());
            for (Report report : reports) {
                int reportId = report.getId();
                PreparedStatement ps = connectionProxy.prepareStatement("select users.* from users join users_reports on users.user_id = users_reports.user_id where by_speaker =? and active_speaker = ? and confirmed = ? and report_id = ?");
            }
            if (!reports.isEmpty()) {
                return reports;
            }
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return reports;
    }

    private User getOfferer(PreparedStatement preparedStatement,int reportId){

    }
}
