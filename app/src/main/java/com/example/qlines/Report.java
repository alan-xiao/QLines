package com.example.qlines;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
        TimeUnit timeUnit = TimeUnit.MINUTES;
        Date current = new Date();
        Date created = this.getTime();
        if (created == null) {
            return "";
        } else {
            long diff = current.getTime() - this.getTime().getTime();
            System.out.println(diff);
            Long ans = timeUnit.convert(diff, TimeUnit.MILLISECONDS);
            if (ans == 0) {
                ans++;
                return ans.toString() + " Minute Ago";
            }
            if (ans % 60 == 0) {
                ans /= 60;
                if (ans == 1) {
                    return ans.toString() + " Hour Ago";
                } else {
                    return ans.toString() + " Hours Ago";
                }
            }
            if (ans > 60) {
                Long hours = ans/60;
                Long minutes = ans%60;
                if (hours == 1) {
                    if (minutes == 1) {
                        return hours.toString() + " Hour " + minutes.toString() + " Minute Ago";
                    } else {
                        return hours.toString() + " Hour " + minutes.toString() + " Minutes Ago";
                    }
                } else {
                    if (minutes == 1) {
                        return hours.toString() + " Hours " + minutes.toString() + " Minute Ago";
                    } else {
                        return hours.toString() + " Hours " + minutes.toString() + " Minutes Ago";
                    }
                }

            } else {
                if (ans == 1) {
                    return ans.toString() + " Minute Ago";
                } else {
                    return ans.toString() + " Minutes Ago";
                }
            }
        }
    }

    public Date getTime() {
        try {
            Date date = new Date((Long) this.getTimestamp());
            System.out.println(date);
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
//            Calendar cal = Calendar.getInstance();
//            SimpleDateFormat now_sdf = new SimpleDateFormat("HH:mm:ss");
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean notShow() {
        TimeUnit timeUnit = TimeUnit.MINUTES;
        Date current = new Date();
        Date created = this.getTime();
        if (created == null) {
            return false;
        } else {
            long diff = current.getTime() - this.getTime().getTime();
            System.out.println(diff);
            Long ans = timeUnit.convert(diff, TimeUnit.MILLISECONDS);
            return ans > 360;
        }
    }
}
