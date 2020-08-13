package com.autonsi.databoard.AlarmData.Dashboard;

public class AlarmDashboardMaster {
    String ss_no,ss_nm,sts_nd,building_code,floor_code,Time;

    public AlarmDashboardMaster(String ss_no, String ss_nm, String sts_nd, String building_code, String floor_code, String time) {
        this.ss_no = ss_no;
        this.ss_nm = ss_nm;
        this.sts_nd = sts_nd;
        this.building_code = building_code;
        this.floor_code = floor_code;
        Time = time;
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

    public String getSts_nd() {
        return sts_nd;
    }

    public void setSts_nd(String sts_nd) {
        this.sts_nd = sts_nd;
    }

    public String getBuilding_code() {
        return building_code;
    }

    public void setBuilding_code(String building_code) {
        this.building_code = building_code;
    }

    public String getFloor_code() {
        return floor_code;
    }

    public void setFloor_code(String floor_code) {
        this.floor_code = floor_code;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
