package com.quickblox.sample.videochat.java.AlarmData.IssuesList;

public class DoorHistoryMaster {
    String id,ss_no,ss_nm,Time,building_code,floor_code,img;

    public DoorHistoryMaster(String id, String ss_no, String ss_nm, String time, String building_code, String floor_code, String img) {
        this.id = id;
        this.ss_no = ss_no;
        this.ss_nm = ss_nm;
        Time = time;
        this.building_code = building_code;
        this.floor_code = floor_code;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
