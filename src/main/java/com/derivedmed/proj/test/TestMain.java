package com.derivedmed.proj.test;

import com.derivedmed.proj.dao.ReportDao;
import com.derivedmed.proj.factory.DaoFactory;
import com.derivedmed.proj.factory.ServiceFactory;
import com.derivedmed.proj.model.Report;
import com.derivedmed.proj.services.ReportService;

import java.util.List;

public class TestMain {
    public static void main(String[] args) {
        ReportService reportService = ServiceFactory.getReportService();
        ReportDao reportDao = DaoFactory.getInstance().getReportDao();
        List<Report> reports = reportService.getReportsOfferedBySpeakerOrModer(2,false);
        for (Report report:reports){
            System.out.println(report);
        }

    }
}
