package com.avikenz.ba.picontrol.control.management;

import android.support.annotation.Nullable;

import com.avikenz.ba.picontrol.control.param.common.SignalType;

import java.util.List;

/**
 * Created by AviKenz on 1/12/2018.
 */

public class PowerPort extends Port {

    public PowerPort(PortType pPortType, int pPinNumberBcm, int pPinNumberBoard, @Nullable List<SignalType> pAcceptedSignals) {
        super(pPortType, pPinNumberBcm, pPinNumberBoard, pAcceptedSignals);
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
