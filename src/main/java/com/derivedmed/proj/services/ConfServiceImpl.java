package com.derivedmed.proj.services;

import com.derivedmed.proj.dao.ConfDao;
import com.derivedmed.proj.factory.DaoFactory;
import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Conf;
import com.derivedmed.proj.model.Report;
import com.derivedmed.proj.model.Role;
import com.derivedmed.proj.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ConfServiceImpl implements ConfService {
    private static ConfServiceImpl ourInstance = new ConfServiceImpl();
    private final ConfDao confDao = DaoFactory.getInstance().getConfDao();
    private final ReportService reportService =ServiceFactory.getReportService();

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
        List<Report> confirmed = reportService.getAll();
        for (Conf conf : confs) {
            conf.setReports(confirmed
                    .stream()
                    .filter(r->r.getConf_id()==conf.getId())
                    .collect(Collectors.toList()));
        }
        return confs;
    }

    @Override
    public List<Conf> getUpcoming(User user) {
        List<Conf> confs = getAll().stream()
                .filter(conf -> conf.getDate().getTime() > new Date().getTime())
                .collect(Collectors.toList());

        if (user.getRole() == Role.ADMINISTRATOR || user.getRole() == Role.MODERATOR) {
            return confs;
        }
        List<Conf> result = new ArrayList<>();
        for (Conf conf : confs) {
            List<Report> reports = new ArrayList<>();
            for (Report report : conf.getReports()) {
                if (report.getSpeakerName() != null) {
                    reports.add(report);
                }
            }
            conf.setReports(reports);
            if (!reports.isEmpty()) {
                result.add(conf);
            }
        }
        return result;
    }

    @Override
    public List<Conf> getPast() {
        List<Conf> result = getAll().stream()
                .filter(conf -> conf.getDate().getTime() < new Date().getTime())
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public boolean delete(int id) {
        return confDao.delete(id);
    }

    @Override
    public boolean deleteAll() {
        return confDao.clearAll();
    }
}
