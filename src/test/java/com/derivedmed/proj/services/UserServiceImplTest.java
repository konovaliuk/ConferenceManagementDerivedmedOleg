package com.derivedmed.proj.services;

import com.derivedmed.proj.model.Conf;
import com.derivedmed.proj.model.Report;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@PrepareForTest({UserServiceImpl.class,ReportService.class})
public class UserServiceImplTest {

    private ReportService reportService = mock(ReportService.class);

    private UserServiceImpl userService;

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
    private final List<Report> reportsFromFirst = new ArrayList<>();
    private final List<Report> reportsFromSecond = new ArrayList<>();
    private final List<Integer> votedByUser = new ArrayList<>();

    @Before
    public void setUpTest() {
        userService = UserServiceImpl.getInstance();
        List<Report> reports = new ArrayList<>();
        reports.add(reportOne);
        reports.add(reportTwo);
        reports.add(reportFourth);
        reportsFromFirst.add(reportOne);
        reportsFromFirst.add(reportTwo);
        reportsFromFirst.add(reportThree);
        reportsFromSecond.add(reportFourth);
        reportsFromSecond.add(reportFifth);
        confOne.setReports(reportsFromFirst);
        confTwo.setReports(reportsFromSecond);
        confs.add(confOne);
        confs.add(confTwo);
        votedByUser.add(1);
        votedByUser.add(3);
        when(reportService.votedByUser(anyInt())).thenReturn(votedByUser);
        when(reportService.getByUserId(anyInt())).thenReturn(reports);
        Whitebox.setInternalState(UserServiceImpl.class,"reportService",reportService);
    }

    @Test
    public void isUserRegistered() {
        Map<Integer, String> isRegistered = userService.isUserRegistered(1, confs);
        long actual = isRegistered.entrySet().stream()
                .filter((e) -> "disabled".equals(e.getValue()))
                .count();
        assertEquals(5, isRegistered.size());
        assertEquals(3,actual);
    }

    @Test
    public void isUserVoted() {
        Map<Integer,String> isUserVoted = userService.isUserVoted(1,confs);
        long actual = isUserVoted.entrySet().stream()
                .filter((e) -> "disabled".equals(e.getValue()))
                .count();
        assertEquals(2,actual);
    }
}