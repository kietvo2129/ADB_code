package com.autonsi.databoard.DigitalData.IssuesReport;

public class IssuesReport {

    private String sensorID;
    private String sensorName;
    private String location;
    private String updateTime;
    private String id;
    private boolean idColor;

    private String Floor;
    private String Building;
    private String img_issue;


    public IssuesReport(String sensorID, String sensorName, String location, String updateTime, String id, boolean idColor, String floor, String building, String img_issue) {
        this.sensorID = sensorID;
        this.sensorName = sensorName;
        this.location = location;
        this.updateTime = updateTime;
        this.id = id;
        this.idColor = idColor;
        this.Floor = floor;
        this.Building = building;
        this.img_issue = img_issue;
    }

    public String getImg_issue() {
        return img_issue;
    }

    public void setImg_issue(String img_issue) {
        this.img_issue = img_issue;
    }

    public String getFloor() {
        return Floor;
    }

    public void setFloor(String floor) {
        Floor = floor;
    }

    public String getBuilding() {
        return Building;
    }

    public void setBuilding(String building) {
        Building = building;
    }
//    public IssuesReport(String sensorID, String sensorName, String location, String updateTime) {
//        this.sensorID = sensorID;
//        this.sensorName = sensorName;
//        this.location = location;
//        this.updateTime = updateTime;
//    }

//    public IssuesReport( String sensorID, String sensorName, String location, String updateTime, String id, boolean idColor) {
//        this.sensorID = sensorID;
//        this.sensorName = sensorName;
//        this.location = location;
//        this.updateTime = updateTime;
//        this.id = id;
//        this.idColor = idColor;
//    }

    public boolean isIdColor() {
        return idColor;
    }

    public void setIdColor(boolean idColor) {
        this.idColor = idColor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSensorID() {
        return sensorID;
    }

    public void setSensorID(String sensorID) {
        this.sensorID = sensorID;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
