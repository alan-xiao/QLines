package com.example.qlines;

import java.io.Serializable;
import java.util.HashMap;

public class Location implements Serializable {

    private String name;
    private String loc_desc;
    private String phone;
    private HashMap<String, Report> reports;

    public HashMap<String, Report> getReports() {
        return reports;
    }

    public void setReports(HashMap<String,Report> reports) {
        this.reports = reports;
    }

    public Location() {

    }

    public Location(String name, String loc_desc, String phone, HashMap<String, Report> reports) {
        this.name = name;
        this.loc_desc = loc_desc;
        this.phone = phone;
        this.reports = reports;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoc_desc() {
        return loc_desc;
    }

    public void setLoc_desc(String loc_desc) {
        this.loc_desc = loc_desc;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return name;
    }
}
