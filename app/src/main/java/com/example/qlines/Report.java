package com.example.qlines;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;

@IgnoreExtraProperties
public class Report implements Serializable {
    private String rep_desc;
    private String user_id;
    private Object timestamp;

    public Report() {
    }

    public Report(String rep_desc, String user_id) {
        this.rep_desc = rep_desc;
        this.user_id = user_id;
        this.timestamp = ServerValue.TIMESTAMP;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    @Exclude
    public Long getTimestamp(boolean isLong) {
        if (timestamp instanceof Long) return (Long) timestamp;
        else return null;
    }

    @Override
    public String toString() {
        return rep_desc + " by " + user_id;
    }

    public Object getTimestampString() {
        if (timestamp instanceof Double) {

      /*    this will handle the case of AFTER fetch from backend,and
            serialize to object into SharedPreferences for example ,when the
            Object casting automatically into Double.
            (Anyway,call (long)getTimestamp from your code who use this model*/
            return ((Double) timestamp).longValue();
        } else {
            return timestamp;  //this handle to firebase requirement in the server side(Object needed)
        }
    }
}
