package com.derivedmed.proj.model;

import com.derivedmed.proj.util.annotations.Column;
import com.derivedmed.proj.util.annotations.Model;

import java.sql.Timestamp;
import java.util.Objects;

@Model
public class MailData {

    @Column(name = "email")
    private String email;

    @Column(name = "login")
    private String userName;

    @Column(name = "conf_name")
    private String confName;

    @Column(name = "conf_place")
    private String confPlace;

    @Column(name = "conf_date")
    private Timestamp confDate;

    @Column(name = "report_name")
    private String reportName;

    public String getEmail() {
        return email;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    public String getConfPlace() {
        return confPlace;
    }

    public void setConfPlace(String confPlace) {
        this.confPlace = confPlace;
    }

    public Timestamp getConfDate() {
        return confDate;
    }

    public void setConfDate(Timestamp confDate) {
        this.confDate = confDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailData mailData = (MailData) o;
        return Objects.equals(email, mailData.email) &&
                Objects.equals(userName, mailData.userName) &&
                Objects.equals(confName, mailData.confName) &&
                Objects.equals(confPlace, mailData.confPlace) &&
                Objects.equals(confDate, mailData.confDate) &&
                Objects.equals(reportName, mailData.reportName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email, userName, confName, confPlace, confDate, reportName);
    }

    @Override
    public String toString() {
        return "MailData{" +
                "email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", confName='" + confName + '\'' +
                ", confPlace='" + confPlace + '\'' +
                ", confDate=" + confDate +
                ", reportName='" + reportName + '\'' +
                '}';
    }
}
