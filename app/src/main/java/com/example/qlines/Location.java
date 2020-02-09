package com.example.qlines;

import java.io.Serializable;

public class Location implements Serializable {

    private String name;
    private String loc_desc;
    private String phone;

    public Location() {

    }

    public Location(String name, String loc_desc, String phone) {
        this.name = name;
        this.loc_desc = loc_desc;
        this.phone = phone;
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
