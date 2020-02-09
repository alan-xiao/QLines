package com.example.qlines;

import java.io.Serializable;

public class Report implements Serializable {
    private String rep_desc;
    private String time;
    private String user_id;

    public Report() {

    }

    public Report(String rep_desc, String time, String user_id) {
        this.rep_desc = rep_desc;
        this.time = time;
        this.user_id = user_id;
    }

    public String getRep_desc() {
        return rep_desc;
    }

    public void setRep_desc(String rep_desc) {
        this.rep_desc = rep_desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
