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
    private String report_name;

    @Column(name = "report_desk")
    private String report_description;

    private String speakerName;

    private String confName;

    private Timestamp date;

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

    public String getReport_name() {
        return report_name;
    }

    public void setReport_name(String report_name) {
        this.report_name = report_name;
    }

    public String getReport_description() {
        return report_description;
    }

    public void setReport_description(String report_description) {
        this.report_description = report_description;
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
                Objects.equals(report_name, report.report_name) &&
                Objects.equals(report_description, report.report_description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, conf_id, report_name, report_description);
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", conf_id=" + conf_id +
                ", report_name='" + report_name + '\'' +
                ", report_description='" + report_description + '\'' +
                ", speakerName='" + speakerName + '\'' +
                ", confName='" + confName + '\'' +
                ", date=" + date +
                '}';
    }
}
