package com.autonsi.databoard.Counting.Count.Detail;

public class DetailMaster {
    String id, work_time,start_time,actual_qty,defect_qty,target_qty,Alarm_Range1,Alarm_Range2;

    public DetailMaster(String id, String work_time, String start_time, String actual_qty, String defect_qty,String target_qty, String Alarm_Range1,String Alarm_Range2) {
        this.id = id;
        this.work_time = work_time;
        this.start_time = start_time;
        this.actual_qty = actual_qty;
        this.defect_qty = defect_qty;
        this.target_qty = target_qty;
        this.Alarm_Range1 = Alarm_Range1;
        this.Alarm_Range2 = Alarm_Range2;
    }

    public String getTarget_qty() {
        return target_qty;
    }

    public void setTarget_qty(String target_qty) {
        this.target_qty = target_qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
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
}
