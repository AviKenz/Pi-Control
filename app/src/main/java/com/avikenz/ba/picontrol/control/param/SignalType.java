package com.avikenz.ba.picontrol.control.param;

/**
 * Created by AviKenz on 1/3/2018.
 */

public enum SignalType {

    DC("DC", 0), PWM("PWM", 1), SPI("SPI", 2), I2C("I2C", 3), UART("UART", 4);

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
