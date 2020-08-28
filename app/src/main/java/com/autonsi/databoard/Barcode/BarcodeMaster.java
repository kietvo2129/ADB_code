package com.autonsi.databoard.Barcode;

public class BarcodeMaster {
    String id,barcode,no;
    boolean checked;

    public BarcodeMaster(String id, String barcode, String no,Boolean checkbox) {
        this.id = id;
        this.barcode = barcode;
        this.no = no;
        checked = checkbox;
    }

    public boolean isChecked(){
        return checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
