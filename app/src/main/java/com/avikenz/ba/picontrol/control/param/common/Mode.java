package com.avikenz.ba.picontrol.control.param.common;

/**
 * Created by AviKenz on 1/3/2018.
 */

public enum Mode {

    BOARD("board", 10), BCM("bcm", 11);

    private String mDescription = "Specify the numbering mode of board";
    private String mName;
    private int mValue;

    Mode(String pName, int pValue) {
        mName = pName;
        mValue = pValue;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getName() {
        return mName;
    }

    public int getValue() {
        return mValue;
    }
}
