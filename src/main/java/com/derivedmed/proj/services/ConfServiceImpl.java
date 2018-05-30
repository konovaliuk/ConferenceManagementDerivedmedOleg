package com.derivedmed.proj.services;

import com.derivedmed.proj.dao.ConfDao;
import com.derivedmed.proj.factory.DaoFactory;
import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Conf;
import com.derivedmed.proj.model.Report;
import com.derivedmed.proj.model.Role;
import com.derivedmed.proj.model.User;
import com.derivedmed.proj.util.annotations.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ConfServiceImpl implements ConfService {
    private static ConfServiceImpl ourInstance = new ConfServiceImpl();
    private static final ConfDao confDao = DaoFactory.getInstance().getConfDao();
    private static final ReportService reportService = ServiceFactory.getReportService();

    public static ConfServiceImpl getInstance() {
        return ourInstance;
    }

    private ConfServiceImpl() {
    }

    @Override
    public int createConf(Conf conf) {
        return confDao.create(conf);
    }

    @Override
    public Conf getById(int id) {
        return confDao.getByID(id);
    }

    @Override
    public boolean update(Conf conf) {
        return confDao.update(conf);
    }

    @Override
    public List<Conf> getAll() {
        List<Conf> confs = confDao.getAll();
        List<Report> reports = reportService.getAll();
        return confs.stream().peek(c -> c.setReports(reports.stream()
                .filter(r -> r.getConf_id() == c.getId())
                .collect(Collectors.toList()))).collect(Collectors.toList());
    }

    @Override
    public List<Conf> getUpcoming(User user) {
        List<Conf> confs = getAll().stream()
                .filter(conf -> conf.getDate().getTime() > new Date().getTime())
                .collect(Collectors.toList());

        if (user.getRole() != Role.USER) {
            return confs;
        }
        return confs.stream()
                .peek(c -> c.setReports(c.getReports().stream()
                        .filter(r -> r.getSpeakerName() != null).collect(Collectors.toList())))
                .filter(c -> !c.getReports().isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public List<Conf> getPast() {
        return getAll().stream()
                .filter(conf -> conf.getDate().getTime() < new Date().getTime())
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public boolean delete(int id) {
        return confDao.delete(id);
    }

    @Override
    public boolean deleteAll() {
        return confDao.clearAll();
    }
}
