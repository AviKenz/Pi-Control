package com.avikenz.ba.picontrol.control.param;

/**
 * Created by AviKenz on 1/16/2018.
 */

public enum PwmOutputType {

    INTEGER("integer", 100), BYTE("byte", 255);

    private String mName;
    private int mMaxValue;
    private String mDescription = "Specify the ouptut type of the PWM signal";

    PwmOutputType(String pName, int pMaxValue) {
        mName = pName;
        mMaxValue = pMaxValue;
    }

    public String getName() {
        return mName;
    }

    public int getmMaxValue() {
        return mMaxValue;
    }
}
