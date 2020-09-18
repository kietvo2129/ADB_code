package com.autonsi.databoard.Ship.Confirm;

public class ConfirmMaster {

    String mrid,mr_no,mr_sts_cd,lct_nm,mt_qty,worker_id,manager_id
    ,req_rec_dt;

    public ConfirmMaster(String mrid, String mr_no, String mr_sts_cd, String lct_nm, String mt_qty, String worker_id, String manager_id, String req_rec_dt) {
        this.mrid = mrid;
        this.mr_no = mr_no;
        this.mr_sts_cd = mr_sts_cd;
        this.lct_nm = lct_nm;
        this.mt_qty = mt_qty;
        this.worker_id = worker_id;
        this.manager_id = manager_id;
        this.req_rec_dt = req_rec_dt;
    }

    public String getMrid() {
        return mrid;
    }

    public void setMrid(String mrid) {
        this.mrid = mrid;
    }

    public String getMr_no() {
        return mr_no;
    }

    public void setMr_no(String mr_no) {
        this.mr_no = mr_no;
    }

    public String getMr_sts_cd() {
        return mr_sts_cd;
    }

    public void setMr_sts_cd(String mr_sts_cd) {
        this.mr_sts_cd = mr_sts_cd;
    }

    public String getLct_nm() {
        return lct_nm;
    }

    public void setLct_nm(String lct_nm) {
        this.lct_nm = lct_nm;
    }

    public String getMt_qty() {
        return mt_qty;
    }

    public void setMt_qty(String mt_qty) {
        this.mt_qty = mt_qty;
    }

    public String getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(String worker_id) {
        this.worker_id = worker_id;
    }

    public String getManager_id() {
        return manager_id;
    }

    public void setManager_id(String manager_id) {
        this.manager_id = manager_id;
    }

    public String getReq_rec_dt() {
        return req_rec_dt;
    }

    public void setReq_rec_dt(String req_rec_dt) {
        this.req_rec_dt = req_rec_dt;
    }
}
