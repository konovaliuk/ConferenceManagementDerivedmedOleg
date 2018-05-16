package com.derivedmed.proj.model;

import com.derivedmed.proj.util.annotations.Column;
import com.derivedmed.proj.util.annotations.Model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Model
public class Conf {

    @Column(name = "conf_id")
    private int id;

    @Column(name = "conf_name")
    private String name;

    @Column(name = "conf_place")
    private String place;

    @Column(name = "conf_date")
    private Timestamp date;

    private List<Report> reports;

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public Conf() {
    }

    public Conf(String name, String place, Timestamp date) {
        this.name = name;
        this.place = place;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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
        if (!(o instanceof Conf)) return false;
        Conf conf = (Conf) o;
        return id == conf.id &&
                Objects.equals(name, conf.name) &&
                Objects.equals(place, conf.place) &&
                Objects.equals(date, conf.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, place, date);
    }

    @Override
    public String toString() {
        return "Conf{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", date=" + date +
                '}';
    }
}
