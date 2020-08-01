package com.quickblox.sample.videochat.java.Counting.DashBoard;

public class DashboardCountingMaster {
    String id,line_no,line_nm,work_ymd,target_qty,work_time,qty_per_hour,update_time
    ,current_act_qty,current_def_qty,latest_act_qty,line_no_d,latest_def_qty
    ,remark,lunch_start_time,lunch_end_time,dinner_start_time,dinner_end_time,Alarm_Range1,Alarm_Range2;

    public DashboardCountingMaster(String id,String line_no, String line_nm, String work_ymd, String target_qty, String work_time, String qty_per_hour, String update_time, String current_act_qty, String current_def_qty, String latest_act_qty, String line_no_d, String latest_def_qty, String remark, String lunch_start_time, String lunch_end_time, String dinner_start_time, String dinner_end_time, String alarm_Range1, String alarm_Range2) {
        this.id  = id;
        this.line_no = line_no;
        this.line_nm = line_nm;
        this.work_ymd = work_ymd;
        this.target_qty = target_qty;
        this.work_time = work_time;
        this.qty_per_hour = qty_per_hour;
        this.update_time = update_time;
        this.current_act_qty = current_act_qty;
        this.current_def_qty = current_def_qty;
        this.latest_act_qty = latest_act_qty;
        this.line_no_d = line_no_d;
        this.latest_def_qty = latest_def_qty;
        this.remark = remark;
        this.lunch_start_time = lunch_start_time;
        this.lunch_end_time = lunch_end_time;
        this.dinner_start_time = dinner_start_time;
        this.dinner_end_time = dinner_end_time;
        Alarm_Range1 = alarm_Range1;
        Alarm_Range2 = alarm_Range2;
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

    public String getWork_ymd() {
        return work_ymd;
    }

    public void setWork_ymd(String work_ymd) {
        this.work_ymd = work_ymd;
    }

    public String getTarget_qty() {
        return target_qty;
    }

    public void setTarget_qty(String target_qty) {
        this.target_qty = target_qty;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public String getQty_per_hour() {
        return qty_per_hour;
    }

    public void setQty_per_hour(String qty_per_hour) {
        this.qty_per_hour = qty_per_hour;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getCurrent_act_qty() {
        return current_act_qty;
    }

    public void setCurrent_act_qty(String current_act_qty) {
        this.current_act_qty = current_act_qty;
    }

    public String getCurrent_def_qty() {
        return current_def_qty;
    }

    public void setCurrent_def_qty(String current_def_qty) {
        this.current_def_qty = current_def_qty;
    }

    public String getLatest_act_qty() {
        return latest_act_qty;
    }

    public void setLatest_act_qty(String latest_act_qty) {
        this.latest_act_qty = latest_act_qty;
    }

    public String getLine_no_d() {
        return line_no_d;
    }

    public void setLine_no_d(String line_no_d) {
        this.line_no_d = line_no_d;
    }

    public String getLatest_def_qty() {
        return latest_def_qty;
    }

    public void setLatest_def_qty(String latest_def_qty) {
        this.latest_def_qty = latest_def_qty;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLunch_start_time() {
        return lunch_start_time;
    }

    public void setLunch_start_time(String lunch_start_time) {
        this.lunch_start_time = lunch_start_time;
    }

    public String getLunch_end_time() {
        return lunch_end_time;
    }

    public void setLunch_end_time(String lunch_end_time) {
        this.lunch_end_time = lunch_end_time;
    }

    public String getDinner_start_time() {
        return dinner_start_time;
    }

    public void setDinner_start_time(String dinner_start_time) {
        this.dinner_start_time = dinner_start_time;
    }

    public String getDinner_end_time() {
        return dinner_end_time;
    }

    public void setDinner_end_time(String dinner_end_time) {
        this.dinner_end_time = dinner_end_time;
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
