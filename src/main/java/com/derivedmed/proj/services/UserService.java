package com.derivedmed.proj.services;

import com.derivedmed.proj.model.Conf;
import com.derivedmed.proj.model.User;

import java.util.HashMap;
import java.util.List;

public interface UserService extends Service {

    int createUser(User user);

    User getUserByID(int id);

    List<User> getAll();

    boolean clearAll();

    boolean updateUser(User user);

    boolean delete(int id);

    boolean registerUserToReport(int userId, int reportId);

    List<User> getSpeakersByRating();

    boolean checkUser(String login, String password);

    User getByLogin(String login);

    boolean vote(int user_id, int report_id, int rating);

    HashMap<Integer, String> isUserRegistered(int userId, List<Conf> confs);

    HashMap<Integer, String> isUserVoted(int userId, List<Conf> confs);
}
