package com.autonsi.databoard.Counting.StatusLayout.MapSensor;

public class CountingMapSensorMaster {

    String id,line_no,line_nm,line_manager,Alarm_Range1,Alarm_Range2,building_nm,floor_nm
    ,remark,reg_id,reg_dt,chg_id,chg_dt,target_qty,actual_qty,defect_qty;

    public CountingMapSensorMaster(String id, String line_no, String line_nm, String line_manager, String alarm_Range1, String alarm_Range2, String building_nm, String floor_nm, String remark, String reg_id, String reg_dt, String chg_id, String chg_dt, String target_qty, String actual_qty, String defect_qty) {
        this.id = id;
        this.line_no = line_no;
        this.line_nm = line_nm;
        this.line_manager = line_manager;
        Alarm_Range1 = alarm_Range1;
        Alarm_Range2 = alarm_Range2;
        this.building_nm = building_nm;
        this.floor_nm = floor_nm;
        this.remark = remark;
        this.reg_id = reg_id;
        this.reg_dt = reg_dt;
        this.chg_id = chg_id;
        this.chg_dt = chg_dt;
        this.target_qty = target_qty;
        this.actual_qty = actual_qty;
        this.defect_qty = defect_qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLine_no() {
        return line_no;
    }

    public void setLine_no(String line_no) {
        this.line_no = line_no;
    }

    public String getLine_nm() {
        return line_nm;
    }

    public void setLine_nm(String line_nm) {
        this.line_nm = line_nm;
    }

    public String getLine_manager() {
        return line_manager;
    }

    public void setLine_manager(String line_manager) {
        this.line_manager = line_manager;
    }

    public String getAlarm_Range1() {
        return Alarm_Range1;
    }

    public void setAlarm_Range1(String alarm_Range1) {
        Alarm_Range1 = alarm_Range1;
    }

    public String getAlarm_Range2() {
        return Alarm_Range2;
    }

    public void setAlarm_Range2(String alarm_Range2) {
        Alarm_Range2 = alarm_Range2;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public String getReg_dt() {
        return reg_dt;
    }

    public void setReg_dt(String reg_dt) {
        this.reg_dt = reg_dt;
    }

    public String getChg_id() {
        return chg_id;
    }

    public void setChg_id(String chg_id) {
        this.chg_id = chg_id;
    }

    public String getChg_dt() {
        return chg_dt;
    }

    public void setChg_dt(String chg_dt) {
        this.chg_dt = chg_dt;
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
}
