package com.avikenz.ba.picontrol.control;

import com.avikenz.ba.picontrol.control.param.common.Mode;

/**
 * Created by AviKenz on 1/3/2018.
 */

public class DcControl extends OutputControl {

    public DcControl(String pName, Mode pMode, int pPinNumber) {
        super(pName, pMode, pPinNumber);
    }
}
