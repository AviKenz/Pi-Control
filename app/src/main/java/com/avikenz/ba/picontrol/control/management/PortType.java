package com.avikenz.ba.picontrol.control.management;


/**
 * Created by AviKenz on 1/9/2018.
 */

public enum PortType {
    GPIO("GPIO"), Power5V("Power5V"),Power3v3("Power3v3"), GROUND("Ground");

    private String mName;

    PortType(String pName) {
        mName = pName;
    }

    public String getName() {
        return mName;
    }
}
