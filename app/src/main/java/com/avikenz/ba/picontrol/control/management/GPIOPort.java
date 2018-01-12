package com.avikenz.ba.picontrol.control.management;

import android.support.annotation.Nullable;

import com.avikenz.ba.picontrol.control.param.common.SignalType;

import java.util.List;

/**
 * Created by AviKenz on 1/12/2018.
 */

public class GPIOPort extends Port {

    public GPIOPort(PortType pPortType, int pPinNumberBcm, int pPinNumberBoard, String pTechDesc, @Nullable List<SignalType> pSupportedSignals) {
        super(pPortType, pPinNumberBcm, pPinNumberBoard, pSupportedSignals);
        setTechDescription(pTechDesc);
    }

    @Override
    protected void setTechDescription(String pTechDesc) {
        mTechDescription = pTechDesc;
    }

    @Override
    protected void setSupportedSignal(List<SignalType> pSupportedSignals) {
        mSupportedSignals = pSupportedSignals;
    }
}
