package com.derivedmed.proj.model;

import com.derivedmed.proj.util.annotations.Column;
import com.derivedmed.proj.util.annotations.Model;

import java.sql.Timestamp;
import java.util.Objects;

@Model
public class Report {
    @Column(name = "report_id")
    private int id;

    @Column(name = "conf_id")
    private int conf_id;

    @Column(name = "report_name")
    private String reportName;

    @Column(name = "report_desk")
    private String reportDescription;

    private String speakerName;

    private String confName;

    private Timestamp date;


    public Report(int id, int conf_id, String reportName, String reportDescription, String confName, Timestamp date) {
        this.id = id;
        this.conf_id = conf_id;
        this.reportName = reportName;
        this.reportDescription = reportDescription;
        this.confName = confName;
        this.date = date;
    }

    public Report() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConf_id() {
        return conf_id;
    }

    public void setConf_id(int conf_id) {
        this.conf_id = conf_id;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getConfName() {
        return confName;
    }

    public void setConfName(String confName) {
        this.confName = confName;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Report)) return false;
        Report report = (Report) o;
        return id == report.id &&
                conf_id == report.conf_id &&
                Objects.equals(reportName, report.reportName) &&
                Objects.equals(reportDescription, report.reportDescription);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, conf_id, reportName, reportDescription);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", conf_id=" + conf_id +
                ", reportName='" + reportName + '\'' +
                ", reportDescription='" + reportDescription + '\'' +
                ", speakerName='" + speakerName + '\'' +
                ", confName='" + confName + '\'' +
                ", date=" + date +
                '}';
    }
}
