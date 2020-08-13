package com.autonsi.databoard.CCTV.CCTVList;

public class CCTVMaster {
    String camera_no,camera_nm,building_code,floor_code;

    public CCTVMaster(String camera_no, String camera_nm, String building_code, String floor_code) {
        this.camera_no = camera_no;
        this.camera_nm = camera_nm;
        this.building_code = building_code;
        this.floor_code = floor_code;
    }

    public String getCamera_no() {
        return camera_no;
    }

    public void setCamera_no(String camera_no) {
        this.camera_no = camera_no;
    }

    public String getCamera_nm() {
        return camera_nm;
    }

    public void setCamera_nm(String camera_nm) {
        this.camera_nm = camera_nm;
    }

    public String getbuilding_code() {
        return building_code;
    }

    public void setbuilding_code(String city_codebuilding_code) {
        this.building_code = city_codebuilding_code;
    }

    public String getFloor_code() {
        return floor_code;
    }

    public void setFloor_code(String floor_code) {
        this.floor_code = floor_code;
    }
}
