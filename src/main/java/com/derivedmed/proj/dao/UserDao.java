package com.derivedmed.proj.dao;

import com.derivedmed.proj.model.Role;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.util.PreparedStatmentCompilier;
import com.derivedmed.proj.util.rsparser.ResultSetParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDao implements CrudDao<User> {

    private static Logger LOGGER = LogManager.getLogger(UserDao.class);
    private static final String SQL_EXCEPTION = "SQL exception user DAO";
    private static final String createSql = "INSERT INTO users (role_id, login, password, email) VALUES (?, ?, ?, ?)";
    private static final String getByIdSql = "select * from users where user_id = ?";
    private static final String updateSql = "update users set login = ?, password = ?, role_id = ?, email = ?, rating = ? where user_id = ?";
    private static final String deleteSql = "delete from users where user_id =?";
    private static final String getSpeakerByReportId = "select user_id from users_reports where report_id = ? and active_speaker = true";
    private static final String checkSpeakerActiveByDateSql = "SELECT users.* " +
            "FROM users " +
            "JOIN users_reports u ON users.user_id = u.user_id " +
            "JOIN reports r ON u.report_id = r.report_id " +
            "JOIN confs c ON r.conf_id = c.conf_id " +
            "WHERE role_id = ? AND active_speaker = ? AND c.confDate = ?";
    private static final String registerUserToReportSql = "insert into users_reports (user_id, report_id) values(?, ?)";
    private static final String getByRatingSql = "select * from users where role_id = ? order by rating desc";
    private static final String authSql = "select * from users where email = ? and password = ?";
    private static final String getByLoginSql = "select * from users where email = ?";
    private static final String getRoleIdSql = "select role_id from roles where role_name = ?";
    private static final String getRoleSql = "select role_name from roles where role_id = ?";
    private static final String voteFirstSql = "update users_reports set rating =? where user_id =? and report_id = ?";
    private static final String voteSecondSql = "update users set rating = rating + ? where user_id = ?";
    private static final String getAllSql = "select * from users";
    private static final ResultSetParser resultSetParser = ResultSetParser.getInstance();
    private static final TransactionManager transactionManager = TransactionManager.getInstance();
    private static final String deleteReportFromUsersReportsSq = "delete from users_reports where user_id = ?";

    @Override
    public int create(User user) {
        int autoGeneratedId;
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connectionProxy
                             .prepareStatement(createSql, Statement.RETURN_GENERATED_KEYS),
                     new Object[]{getRoleId(user.getRole()), user.getLogin(), user.getPassword(), user.getEmail()})) {
            preparedStatement.executeUpdate();
            ResultSet id = preparedStatement.getGeneratedKeys();
            id.next();
            autoGeneratedId = id.getInt(1);
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
            autoGeneratedId = 0;
        }
        return autoGeneratedId;
    }

    @Override
    public User getByID(int id) {
        User user = new User();
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connectionProxy
                     .prepareStatement(getByIdSql), new Object[]{id})) {
            List<User> resultList = resultSetParser.parse(preparedStatement.executeQuery(), user);
            if (!resultList.isEmpty()) {
                user = resultList.get(0);
                user.setRole(getRole(user.getRole_id()));
            }
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return user;
    }

    @Override
    public boolean update(User user) {
        boolean result = true;
        user.setRole_id(getRoleId(user.getRole()));
        Object[] values = new Object[]{user.getLogin(), user.getPassword(), user.getRole_id(), user.getEmail(), user.getRating(), user.getId()};
        try (ConnectionProxy connection = transactionManager.getConnection();
             PreparedStatement preparedStatement = PreparedStatmentCompilier
                     .setValues(connection.prepareStatement(updateSql), values)) {
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
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(deleteSql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
            result = false;
        }
        return result;
    }

    @Override
    public List<User> getAll() {
        List<User> resultList = new ArrayList<>();
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(getAllSql)) {
            resultList = resultSetParser.parse(preparedStatement.executeQuery(), new User());
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return resultList.stream().peek(user -> {
            Role role = getRole(user.getRole_id());
            user.setRole(role);
            user.setRoleString(role.name());
        }).collect(Collectors.toList());
    }

    @Override
    public boolean clearAll() {
        boolean result = true;
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             Statement statement = connectionProxy.createStatement()) {
            String DELETE_ALL_SQL = "DELETE FROM users";
            statement.executeUpdate(DELETE_ALL_SQL);
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
            result = false;
        }
        return result;
    }

    public boolean deleteFromUsersReports(int userId) {
        boolean result = false;
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connectionProxy
                     .prepareStatement(deleteReportFromUsersReportsSq), new Object[]{userId})) {
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return result;
    }


    public boolean vote(int userId, int reportId, int rating) {
        boolean result = true;
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(voteFirstSql);
             PreparedStatement preparedStatement1 = connectionProxy.prepareStatement(voteSecondSql)) {
            preparedStatement.setInt(1, rating);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, reportId);
            preparedStatement.executeUpdate();
            preparedStatement1.setInt(1, rating);
            preparedStatement1.setInt(2, getSpeakerByReportId(connectionProxy, reportId));
            preparedStatement1.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
            result = false;
        }
        return result;
    }

    private int getSpeakerByReportId(ConnectionProxy connectionProxy, int reportId) {
        int id = 0;
        try (PreparedStatement preparedStatement = connectionProxy.prepareStatement(getSpeakerByReportId)) {
            preparedStatement.setInt(1, reportId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return id;
    }

    public List<User> getSpeakersFreeThisDate(Timestamp timestamp) {
        List<User> busyspeakers = busySpeakersByDate(timestamp);
        List<User> speakers = getSpeakersByRating();
        return speakers.stream().filter(s -> !busyspeakers.contains(s))
                .collect(Collectors.toList());
    }

    public List<User> busySpeakersByDate(Timestamp timestamp) {
        List<User> busySpeakers = new ArrayList<>();
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = PreparedStatmentCompilier.setValues(connectionProxy.prepareStatement(checkSpeakerActiveByDateSql),
                     new Object[]{getRoleId(Role.SPEAKER), true, timestamp})) {
            busySpeakers = resultSetParser.parse(preparedStatement.executeQuery(), new User());
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return busySpeakers;
    }

    public boolean registerUserToReport(int userId, int reportId) {
        boolean result = true;
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(registerUserToReportSql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, reportId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
            result = false;
        }
        return result;
    }

    public List<User> getSpeakersByRating() {
        ArrayList<User> resultList = new ArrayList<>();
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(getByRatingSql)) {
            preparedStatement.setInt(1, getRoleId(Role.SPEAKER));
            resultList = resultSetParser.parse(preparedStatement.executeQuery(), new User());
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return resultList;
    }


    public boolean authUser(String login, String password) {
        boolean result = false;
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = connectionProxy
                     .prepareStatement(authSql)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            result = preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return result;
    }

    public User getByLogin(String login) {
        User user = new User();
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = connectionProxy
                     .prepareStatement(getByLoginSql)) {
            preparedStatement.setString(1, login);
            ArrayList<User> users = resultSetParser.parse(preparedStatement.executeQuery(), new User());
            if (!users.isEmpty()) {
                user = users.get(0);
                user.setRole(getRole(user.getRole_id()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private int getRoleId(Role role) {
        int roleId = 0;
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(getRoleIdSql)) {
            preparedStatement.setString(1, role.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                roleId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return roleId;
    }

    private Role getRole(int id) {
        Role role = null;
        try (ConnectionProxy connectionProxy = transactionManager.getConnection();
             PreparedStatement preparedStatement = connectionProxy.prepareStatement(getRoleSql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                role = Role.valueOf(resultSet.getString(1).toUpperCase());
            }
        } catch (SQLException e) {
            LOGGER.error(SQL_EXCEPTION, e);
        }
        return role;
    }
}
