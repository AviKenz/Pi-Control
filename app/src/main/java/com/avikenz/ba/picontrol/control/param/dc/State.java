package com.avikenz.ba.picontrol.control.param.dc;

/**
 * Created by AviKenz on 1/3/2018.
 */

public enum State {

    OFF("off", 0), ON("on", 1);

    private String mDescription = "Specify the DC signal type";
    private String mName;
    private int mValue;

    State(String pName, int pValue) {
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
