package com.example.qlines;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@IgnoreExtraProperties
public class Report implements Serializable {
    private String rep_desc;
    private String user_id;
    private Object timestamp;

    public String getRep_desc() {
        return rep_desc;
    }

    public void setRep_desc(String rep_desc) {
        this.rep_desc = rep_desc;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public Report() {
    }

    public Report(String rep_desc, String user_id) {
        this.rep_desc = rep_desc;
        this.user_id = user_id;
        this.timestamp = ServerValue.TIMESTAMP;
    }

    @Override
    public String toString() {
        return rep_desc + " by " + user_id + " at " + this.getTimestamp().toString();
    }

    public Object getTimestamp() {
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

    public String getTimeAgoString() {
        try {
            Date date = new Date((Long) this.getTimestamp());
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat now_sdf = new SimpleDateFormat("HH:mm:ss");
            String ans = sdf.format(date);
            if (ans.substring(0,1).equals("0")) {
                return ans.substring(1);
            } else {
                return sdf.format(date);
            }
        } catch (Exception e) {
            return "";
        }
    }
}
