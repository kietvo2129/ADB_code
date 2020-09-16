package com.autonsi.databoard.Ship;

public class PickManualItem {
    String id;
    String mr_no;
    String mrd_no;
    String ml_no;
    String mt_cd;
    String mt_type;
    String lot_qty;
    String mt_sts_cd;
    String mt_sts_cd_cd;
    String mt_nm;
    String worker;
    String lct_cd;
    String work_dt;
boolean check;
boolean pickstatus;
    public PickManualItem(boolean check ,boolean pickstatus, String id, String mr_no, String mrd_no, String ml_no, String mt_cd, String mt_type, String lot_qty, String mt_sts_cd, String mt_sts_cd_cd, String mt_nm, String worker, String lct_cd, String work_dt) {
        this.id = id;
        this.mr_no = mr_no;
        this.mrd_no = mrd_no;
        this.ml_no = ml_no;
        this.mt_cd = mt_cd;
        this.mt_type = mt_type;
        this.lot_qty = lot_qty;
        this.mt_sts_cd = mt_sts_cd;
        this.mt_sts_cd_cd = mt_sts_cd_cd;
        this.mt_nm = mt_nm;
        this.worker = worker;
        this.lct_cd = lct_cd;
        this.work_dt = work_dt;
        this.check = check;
        this.pickstatus = pickstatus;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isPickstatus() {
        return pickstatus;
    }

    public void setPickstatus(boolean pickstatus) {
        this.pickstatus = pickstatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMr_no() {
        return mr_no;
    }

    public void setMr_no(String mr_no) {
        this.mr_no = mr_no;
    }

    public String getMrd_no() {
        return mrd_no;
    }

    public void setMrd_no(String mrd_no) {
        this.mrd_no = mrd_no;
    }

    public String getMl_no() {
        return ml_no;
    }

    public void setMl_no(String ml_no) {
        this.ml_no = ml_no;
    }

    public String getMt_cd() {
        return mt_cd;
    }

    public void setMt_cd(String mt_cd) {
        this.mt_cd = mt_cd;
    }

    public String getMt_type() {
        return mt_type;
    }

    public void setMt_type(String mt_type) {
        this.mt_type = mt_type;
    }

    public String getLot_qty() {
        return lot_qty;
    }

    public void setLot_qty(String lot_qty) {
        this.lot_qty = lot_qty;
    }

    public String getMt_sts_cd() {
        return mt_sts_cd;
    }

    public void setMt_sts_cd(String mt_sts_cd) {
        this.mt_sts_cd = mt_sts_cd;
    }

    public String getMt_sts_cd_cd() {
        return mt_sts_cd_cd;
    }

    public void setMt_sts_cd_cd(String mt_sts_cd_cd) {
        this.mt_sts_cd_cd = mt_sts_cd_cd;
    }

    public String getMt_nm() {
        return mt_nm;
    }

    public void setMt_nm(String mt_nm) {
        this.mt_nm = mt_nm;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getLct_cd() {
        return lct_cd;
    }

    public void setLct_cd(String lct_cd) {
        this.lct_cd = lct_cd;
    }

    public String getWork_dt() {
        return work_dt;
    }

    public void setWork_dt(String work_dt) {
        this.work_dt = work_dt;
    }
}
