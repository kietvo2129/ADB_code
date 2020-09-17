package com.autonsi.databoard.Receving.ReceivingScan;

public class PutAwayScanMaster {
    String mt_lot_cd,mt_nm,bin_name;

    public PutAwayScanMaster(String mt_lot_cd, String mt_nm, String bin_name) {
        this.mt_lot_cd = mt_lot_cd;
        this.mt_nm = mt_nm;
        this.bin_name = bin_name;
    }

    public String getMt_lot_cd() {
        return mt_lot_cd;
    }

    public void setMt_lot_cd(String mt_lot_cd) {
        this.mt_lot_cd = mt_lot_cd;
    }

    public String getMt_nm() {
        return mt_nm;
    }

    public void setMt_nm(String mt_nm) {
        this.mt_nm = mt_nm;
    }

    public String getBin_name() {
        return bin_name;
    }

    public void setBin_name(String bin_name) {
        this.bin_name = bin_name;
    }
}
