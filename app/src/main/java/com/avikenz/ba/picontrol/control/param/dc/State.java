package com.avikenz.ba.picontrol.control.param.dc;

/**
 * Created by AviKenz on 1/3/2018.
 */

public enum State {

    OFF("off", false), ON("on", true);

    private String mDescription = "Specify the DC signal type";
    private String mName;
    private boolean mValue;

    State(String pName, boolean pValue) {
        mName = pName;
        mValue = pValue;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getName() {
        return mName;
    }

    public boolean getValue() {
        return mValue;
    }
}
