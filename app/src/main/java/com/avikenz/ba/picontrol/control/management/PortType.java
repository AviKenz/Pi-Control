package com.avikenz.ba.picontrol.control.management;


/**
 * Created by AviKenz on 1/9/2018.
 */

public enum PortType {
    // TODO [M] implement others type;
    GPIO("GPIO"), Power("Power"), GROUND("Ground");

    String mName;

    PortType(String pName) {
        mName = pName;
    }

    public String getName() {
        return mName;
    }
}
