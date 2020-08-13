package com.autonsi.databoard.DigitalData.StatusLayout.Mapsensor;

public class MapMater {
    String ssid,ss_no,ss_nm,top_position,left_posistion,type;

    public MapMater(String ssid, String ss_no, String ss_nm, String top_position, String left_posistion, String type) {
        this.ssid = ssid;
        this.ss_no = ss_no;
        this.ss_nm = ss_nm;
        this.top_position = top_position;
        this.left_posistion = left_posistion;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
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

    public String getTop_position() {
        return top_position;
    }

    public void setTop_position(String top_position) {
        this.top_position = top_position;
    }

    public String getLeft_posistion() {
        return left_posistion;
    }

    public void setLeft_posistion(String left_posistion) {
        this.left_posistion = left_posistion;
    }
}