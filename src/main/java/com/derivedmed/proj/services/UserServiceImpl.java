package com.derivedmed.proj.services;

import com.derivedmed.proj.dao.UserDao;
import com.derivedmed.proj.factory.DaoFactory;
import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Conf;
import com.derivedmed.proj.model.Report;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.util.annotations.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;
    private final UserDao userDao = DaoFactory.getInstance().getUserDao();
    private static final ReportService reportService = ServiceFactory.getReportService();

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    private UserServiceImpl() {
    }

    @Override
    public int createUser(User user) {
        return userDao.create(user);
    }

    @Override
    public User getUserByID(int id) {
        return userDao.getByID(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public boolean clearAll() {
        return userDao.clearAll();
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(int id) {
        return userDao.delete(id);
    }

    @Override
    public boolean registerUserToReport(int userId, int reportId) {
        return userDao.registerUserToReport(userId, reportId);
    }

    @Override
    public List<User> getSpeakersByRating() {
        return userDao.getSpeakersByRating();
    }

    @Override
    public boolean checkUser(String login, String password) {
        return userDao.authUser(login, password);
    }

    @Override
    public User getByLogin(String login) {
        return userDao.getByLogin(login);
    }

    @Override
    public HashMap<Integer, String> isUserRegistered(int userId, List<Conf> confs) {
        List<Report> usersReports = reportService.getByUserId(userId);
        List<Integer> reportsIds = new ArrayList<>();
        HashMap<Integer, String> isRegistered = new HashMap<>();

        for (Report r : usersReports) {
            reportsIds.add(r.getId());
        }
        for (Conf conf : confs) {
            for (Report report : conf.getReports()) {
                int id = report.getId();
                if (reportsIds.contains(id)) {
                    isRegistered.put(id, "disabled");
                } else {
                    isRegistered.put(id, "");
                }
            }
        }

        return isRegistered;
    }

    @Override
    public HashMap<Integer, String> isUserVoted(int userId, List<Conf> confs) {
        List<Integer> votedReports = reportService.votedByUser(userId);
        HashMap<Integer, String> voted = new HashMap<>();
        for (Conf conf : confs) {
            for (Report report : conf.getReports()) {
                if (votedReports.contains(report.getId())) {
                    voted.put(report.getId(), "disabled");
                } else {
                    voted.put(report.getId(), "");
                }
            }
        }
        return voted;
    }

    @Transactional
    @Override
    public boolean vote(int user_id, int report_id, int rating) {
        return userDao.vote(user_id, report_id, rating);
    }

    @Override
    public List<User> getFreeThisDate(Timestamp timestamp) {
        return userDao.getSpeakersFreeThisDate(timestamp);
    }
}
