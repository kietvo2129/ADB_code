package com.autonsi.databoard.Ship.Confirm.detail;

public class ConfirmdetailChildMaster {

    String mt_lot_cd,lot_qty,expiry_date;

    public ConfirmdetailChildMaster(String mt_lot_cd, String lot_qty, String expiry_date) {
        this.mt_lot_cd = mt_lot_cd;
        this.lot_qty = lot_qty;
        this.expiry_date = expiry_date;
    }

    public String getMt_lot_cd() {
        return mt_lot_cd;
    }

    public void setMt_lot_cd(String mt_lot_cd) {
        this.mt_lot_cd = mt_lot_cd;
    }

    public String getLot_qty() {
        return lot_qty;
    }

    public void setLot_qty(String lot_qty) {
        this.lot_qty = lot_qty;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }
}
