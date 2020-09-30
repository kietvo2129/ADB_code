package com.autonsi.databoard.Counting.Count.QCCheck;

public class QCCheckDetailChartMaster {

    String check_value;
    int def_qty;

    public QCCheckDetailChartMaster(String check_value, int def_qty) {
        this.check_value = check_value;
        this.def_qty = def_qty;
    }

    public String getCheck_value() {
        return check_value;
    }

    public void setCheck_value(String check_value) {
        this.check_value = check_value;
    }

    public int getDef_qty() {
        return def_qty;
    }

    public void setDef_qty(int def_qty) {
        this.def_qty = def_qty;
    }
}
