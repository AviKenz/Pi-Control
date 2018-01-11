package com.avikenz.ba.picontrol.control.param.common;

/**
 * Created by AviKenz on 1/3/2018.
 */

public enum SignalType {

    DC("dc", 0), PWM("pwm", 1);

    private String mDescription = "Specify the output signal type";
    private String mName;
    private int mValue;

    SignalType(String pName, int pValue) {
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
