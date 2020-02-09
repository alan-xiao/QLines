package com.example.qlines;

import java.io.Serializable;

public class Report implements Serializable {
    private String rep_desc;
    private String timestamp;
    private String user_id;

    public Report() {

    }

    public String getRep_desc() {
        return rep_desc;
    }

    public void setRep_desc(String rep_desc) {
        this.rep_desc = rep_desc;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Report(String rep_desc, String timestamp, String user_id) {
        this.rep_desc = rep_desc;
        this.timestamp = timestamp;
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Timestamp: " + this.timestamp + " Desc: " + this.rep_desc + " user_id: " + this.user_id;
    }
}
