package com.avikenz.ba.picontrol.control.param.common;

/**
 * Created by AviKenz on 1/9/2018.
 */

public enum PortType {

    GPIO("GPIO"), I2C("I2C");

    String mName;

    PortType(String pName) {
        mName = pName;
    }

    public String getValue() {
        return mName;
    }
}
