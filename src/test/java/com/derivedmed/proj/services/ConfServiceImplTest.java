package com.derivedmed.proj.services;

import com.derivedmed.proj.dao.ConfDao;
import com.derivedmed.proj.model.Conf;
import com.derivedmed.proj.model.Report;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@PrepareForTest({ConfDao.class,ReportService.class,ConfServiceImpl.class})
public class ConfServiceImplTest {
    ConfDao confDao = mock(ConfDao.class);
    ReportService reportService = mock(ReportService.class);
    ConfServiceImpl confService = ConfServiceImpl.getInstance();
    private final Timestamp date = new Timestamp(new Date().getTime() + DateUtils.MILLIS_PER_DAY);
    private final Timestamp date1 = new Timestamp(date.getTime() + DateUtils.MILLIS_PER_DAY);
    private final Report reportOne = new Report(1, 1, "First report", "Desc", "Conf One", date);
    private final Report reportTwo = new Report(2, 1, "Second", "Desc", "Conf One", date);
    private final Report reportThree = new Report(3, 1, "Third", "Desc", "Conf One", date);
    private final Report reportFourth = new Report(4, 2, "Fourth", "Desc", "Conf two", date1);
    private final Report reportFifth = new Report(5, 2, "Fifth", "Desc", "Conf two", date1);
    private final Conf confOne = new Conf(1, "Conf One", "Kiev", date);
    private final Conf confTwo = new Conf(2, "Conf Two", "Kiev", date1);
    private final List<Conf> confs = new ArrayList<>();
    private final List<Report> reports = new ArrayList<>();

    @Before
    public void setupTest(){
        confs.add(confOne);
        confs.add(confTwo);
        reports.add(reportOne);
        reports.add(reportTwo);
        reports.add(reportThree);
        reports.add(reportFourth);
        reports.add(reportFifth);
        when(confDao.getAll()).thenReturn(confs);
        when(reportService.getAll()).thenReturn(reports);
        Whitebox.setInternalState(ConfServiceImpl.class,"reportService",reportService);
        Whitebox.setInternalState(ConfServiceImpl.class,"confDao",confDao);
    }

    @Test
    public void getAll() {
        List<Conf> confs = confService.getAll();
        assertEquals(2,confs.size());
    }

    @Test
    public void getUpcoming() {
    }

    @Test
    public void getPast() {
    }
}