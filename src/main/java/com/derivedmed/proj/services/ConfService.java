package com.derivedmed.proj.services;

import com.derivedmed.proj.model.Conf;
import com.derivedmed.proj.model.User;

import java.util.List;

public interface ConfService extends Service {

    int createConf(Conf conf);

    Conf getById(int id);

    boolean update(Conf conf);

    List<Conf> getAll();

    List<Conf> getUpcoming(User user);

    List<Conf> getPast();


    boolean delete(int id);

    boolean deleteAll();
}
