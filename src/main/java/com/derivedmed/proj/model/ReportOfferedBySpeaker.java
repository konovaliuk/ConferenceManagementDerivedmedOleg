package com.derivedmed.proj.model;

import com.derivedmed.proj.util.annotations.Column;
import com.derivedmed.proj.util.annotations.Model;

import java.sql.Timestamp;
import java.util.Objects;

@Model
public class ReportOfferedBySpeaker {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "report_id")
    private int reportId;

    @Column(name = "conf_id")
    private int confId;

    @Column(name = "conf_name")
    private String confName;

    @Column(name = "conf_date")
    private Timestamp confDate;

    @Column(name = "report_name")
    private String reportName;

    @Column(name = "login")
    private String userName;

    @Column(name = "confirmed")
    private boolean confirmed;

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public int getConfId() {
        return confId;
    }

    public void setConfId(int confId) {
        this.confId = confId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    public Timestamp getConfDate() {
        return confDate;
    }

    public void setConfDate(Timestamp confDate) {
        this.confDate = confDate;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportOfferedBySpeaker that = (ReportOfferedBySpeaker) o;
        return Objects.equals(confName, that.confName) &&
                Objects.equals(confDate, that.confDate) &&
                Objects.equals(reportName, that.reportName) &&
                Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(confName, confDate, reportName, userName);
    }

    @Override
    public String toString() {
        return "ReportOfferedBySpeaker{" +
                "confName='" + confName + '\'' +
                ", confDate=" + confDate +
                ", reportName='" + reportName + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
