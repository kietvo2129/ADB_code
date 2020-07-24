package com.quickblox.sample.videochat.java.AlarmData.StatusLayout.MapSensor;

public class MapsensorAlarmMaster {
    String ss_no,ss_nm,lct_nm,time_up;

    public MapsensorAlarmMaster(String ss_no, String ss_nm, String lct_nm, String time_up) {
        this.ss_no = ss_no;
        this.ss_nm = ss_nm;
        this.lct_nm = lct_nm;
        this.time_up = time_up;
    }

    public String getSs_no() {
        return ss_no;
    }

    public void setSs_no(String ss_no) {
        this.ss_no = ss_no;
    }

    public String getSs_nm() {
        return ss_nm;
    }

    public void setSs_nm(String ss_nm) {
        this.ss_nm = ss_nm;
    }

    public String getLct_nm() {
        return lct_nm;
    }

    public void setLct_nm(String lct_nm) {
        this.lct_nm = lct_nm;
    }

    public String getTime_up() {
        return time_up;
    }

    public void setTime_up(String time_up) {
        this.time_up = time_up;
    }
}
