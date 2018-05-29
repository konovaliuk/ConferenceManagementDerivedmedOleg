package com.derivedmed.proj.model;

import com.derivedmed.proj.util.annotations.Column;
import com.derivedmed.proj.util.annotations.Model;

import java.sql.Timestamp;

@Model
public class ReportWithConf {
    @Column(name = "conf_name")
    private String confName;

    @Column(name = "confDate")
    private Timestamp confDate;

    @Column(name = "report_id")
    private int reportId;

    public ReportWithConf() {
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

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }
}
