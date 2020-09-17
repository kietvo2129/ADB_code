package com.autonsi.databoard.Receving.FindQRCode;

public class FindQRCodeMaster {
    String mt_cd,mt_cd_origin,mt_nm,lot_qty,unit_price,lot_price,expiry_date,mr_no,mrd_no,worker,work_dt,lct_nm,status;

    public FindQRCodeMaster(String mt_cd, String mt_cd_origin, String mt_nm, String lot_qty, String unit_price, String lot_price, String expiry_date, String mr_no, String mrd_no, String worker, String work_dt, String lct_nm, String status) {
        this.mt_cd = mt_cd;
        this.mt_cd_origin = mt_cd_origin;
        this.mt_nm = mt_nm;
        this.lot_qty = lot_qty;
        this.unit_price = unit_price;
        this.lot_price = lot_price;
        this.expiry_date = expiry_date;
        this.mr_no = mr_no;
        this.mrd_no = mrd_no;
        this.worker = worker;
        this.work_dt = work_dt;
        this.lct_nm = lct_nm;
        this.status = status;
    }

    public String getMt_cd() {
        return mt_cd;
    }

    public void setMt_cd(String mt_cd) {
        this.mt_cd = mt_cd;
    }

    public String getMt_cd_origin() {
        return mt_cd_origin;
    }

    public void setMt_cd_origin(String mt_cd_origin) {
        this.mt_cd_origin = mt_cd_origin;
    }

    public String getMt_nm() {
        return mt_nm;
    }

    public void setMt_nm(String mt_nm) {
        this.mt_nm = mt_nm;
    }

    public String getLot_qty() {
        return lot_qty;
    }

    public void setLot_qty(String lot_qty) {
        this.lot_qty = lot_qty;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public String getLot_price() {
        return lot_price;
    }

    public void setLot_price(String lot_price) {
        this.lot_price = lot_price;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
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

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getWork_dt() {
        return work_dt;
    }

    public void setWork_dt(String work_dt) {
        this.work_dt = work_dt;
    }

    public String getLct_nm() {
        return lct_nm;
    }

    public void setLct_nm(String lct_nm) {
        this.lct_nm = lct_nm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
