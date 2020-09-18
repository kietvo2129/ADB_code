package com.autonsi.databoard.Ship.Confirm.detail;

public class ConfirmdetailMaster {
    String mr_no,mr_sts_cd,mrdid,mt_no,mrd_no,req_bundle_qty1,req_qty1,req_bundle_qty,req_qty,mt_nm;

    public ConfirmdetailMaster(String mr_no, String mr_sts_cd, String mrdid, String mt_no, String mrd_no, String req_bundle_qty1, String req_qty1, String req_bundle_qty, String req_qty, String mt_nm) {
        this.mr_no = mr_no;
        this.mr_sts_cd = mr_sts_cd;
        this.mrdid = mrdid;
        this.mt_no = mt_no;
        this.mrd_no = mrd_no;
        this.req_bundle_qty1 = req_bundle_qty1;
        this.req_qty1 = req_qty1;
        this.req_bundle_qty = req_bundle_qty;
        this.req_qty = req_qty;
        this.mt_nm = mt_nm;
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

    public String getMrdid() {
        return mrdid;
    }

    public void setMrdid(String mrdid) {
        this.mrdid = mrdid;
    }

    public String getMt_no() {
        return mt_no;
    }

    public void setMt_no(String mt_no) {
        this.mt_no = mt_no;
    }

    public String getMrd_no() {
        return mrd_no;
    }

    public void setMrd_no(String mrd_no) {
        this.mrd_no = mrd_no;
    }

    public String getReq_bundle_qty1() {
        return req_bundle_qty1;
    }

    public void setReq_bundle_qty1(String req_bundle_qty1) {
        this.req_bundle_qty1 = req_bundle_qty1;
    }

    public String getReq_qty1() {
        return req_qty1;
    }

    public void setReq_qty1(String req_qty1) {
        this.req_qty1 = req_qty1;
    }

    public String getReq_bundle_qty() {
        return req_bundle_qty;
    }

    public void setReq_bundle_qty(String req_bundle_qty) {
        this.req_bundle_qty = req_bundle_qty;
    }

    public String getReq_qty() {
        return req_qty;
    }

    public String getMt_nm() {
        return mt_nm;
    }

    public void setMt_nm(String mt_nm) {
        this.mt_nm = mt_nm;
    }

    public void setReq_qty(String req_qty) {
        this.req_qty = req_qty;
    }
}
