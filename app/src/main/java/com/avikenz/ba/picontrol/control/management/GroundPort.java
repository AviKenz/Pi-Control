package com.avikenz.ba.picontrol.control.management;

import android.support.annotation.Nullable;

import com.avikenz.ba.picontrol.control.param.SignalType;

import java.util.List;

/**
 * Created by AviKenz on 1/12/2018.
 */

public class GroundPort extends Port {

    public GroundPort(int pPinNumberBoard, @Nullable List<SignalType> pAcceptedSignals) {
        super(PortType.GROUND, pPinNumberBoard, pPinNumberBoard, pAcceptedSignals);
    }

    @Override
    protected void setTechDescription(String pTechDesc) {
        mTechDescription = getType().getName();
    }

    @Override
    protected void setSupportedSignal(List<SignalType> pSupportedSignals) {
        mSupportedSignals = null;
    }
}
