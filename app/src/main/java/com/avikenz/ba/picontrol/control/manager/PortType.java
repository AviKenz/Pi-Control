package com.avikenz.ba.picontrol.control.manager;

/**
 * Created by AviKenz on 1/9/2018.
 */

public enum PortType {

    GPIO("GPIO"), Power("Power"), GROUND("Ground");

    String mName;

    PortType(String pName) {
        mName = pName;
    }

    public String getValue() {
        return mName;
    }
}
