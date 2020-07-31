package com.quickblox.sample.videochat.java.Counting.Count.Line;

public class DataAllCountingMaster {
    String building_nm,floor_nm,target_qty,actual_qty,defect_qty,start_time,end_time;

    public DataAllCountingMaster(String building_nm, String floor_nm, String target_qty, String actual_qty, String defect_qty, String start_time, String end_time) {
        this.building_nm = building_nm;
        this.floor_nm = floor_nm;
        this.target_qty = target_qty;
        this.actual_qty = actual_qty;
        this.defect_qty = defect_qty;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public String getBuilding_nm() {
        return building_nm;
    }

    public void setBuilding_nm(String building_nm) {
        this.building_nm = building_nm;
    }

    public String getFloor_nm() {
        return floor_nm;
    }

    public void setFloor_nm(String floor_nm) {
        this.floor_nm = floor_nm;
    }

    public String getTarget_qty() {
        return target_qty;
    }

    public void setTarget_qty(String target_qty) {
        this.target_qty = target_qty;
    }

    public String getActual_qty() {
        return actual_qty;
    }

    public void setActual_qty(String actual_qty) {
        this.actual_qty = actual_qty;
    }

    public String getDefect_qty() {
        return defect_qty;
    }

    public void setDefect_qty(String defect_qty) {
        this.defect_qty = defect_qty;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
