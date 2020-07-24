package com.quickblox.sample.videochat.java.DigitalData.StatusLayout.MapBuild;

public class MapBuildMatter {
    String building_id,building_name,building_code,city_code;

    public MapBuildMatter(String building_id, String building_name, String building_code, String city_code) {
        this.building_id = building_id;
        this.building_name = building_name;
        this.building_code = building_code;
        this.city_code = city_code;
    }

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getBuilding_code() {
        return building_code;
    }

    public void setBuilding_code(String building_code) {
        this.building_code = building_code;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }
}
