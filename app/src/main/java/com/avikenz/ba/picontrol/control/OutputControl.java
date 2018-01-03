package com.avikenz.ba.picontrol.control;

import com.avikenz.ba.picontrol.control.param.common.Direction;
import com.avikenz.ba.picontrol.control.param.common.Mode;

/**
 * Created by AviKenz on 1/3/2018.
 */

public abstract class OutputControl extends Control {

    public OutputControl(String pName, Mode pMode, int pPinNumber) {
        super(pName, pMode, pPinNumber);
    }

    @Override
    protected void setDirection(Direction pDirection) {
        mDirection = Direction.OUT;
    }

    @Override
    protected void setDescription(String pDescription) {

    }
}
