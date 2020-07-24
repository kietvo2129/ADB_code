package com.quickblox.sample.videochat.java.DigitalData.StatusLayout.MapFloor;

public class MapFloorMatter {
    String no,floor_id,floor_name,floor_code,floor_image,building_code;

    public MapFloorMatter(String no,String floor_id, String floor_name, String floor_code, String floor_image, String building_code) {
        this.no = no;
        this.floor_id = floor_id;
        this.floor_name = floor_name;
        this.floor_code = floor_code;
        this.floor_image = floor_image;
        this.building_code = building_code;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(String floor_id) {
        this.floor_id = floor_id;
    }

    public String getFloor_name() {
        return floor_name;
    }

    public void setFloor_name(String floor_name) {
        this.floor_name = floor_name;
    }

    public String getFloor_code() {
        return floor_code;
    }

    public void setFloor_code(String floor_code) {
        this.floor_code = floor_code;
    }

    public String getFloor_image() {
        return floor_image;
    }

    public void setFloor_image(String floor_image) {
        this.floor_image = floor_image;
    }

    public String getBuilding_code() {
        return building_code;
    }

    public void setBuilding_code(String building_code) {
        this.building_code = building_code;
    }
}
