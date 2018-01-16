package com.avikenz.ba.picontrol.control.management;

import android.support.annotation.Nullable;

import com.avikenz.ba.picontrol.control.param.common.Mode;
import com.avikenz.ba.picontrol.control.param.common.SignalType;

import java.util.List;

/**
 * Created by AviKenz on 1/11/2018.
 * represent a port on the raspberry pi.
 */

public abstract class Port {

    private PortType mType;
    private int mPinNumberBcm;
    private int mPinNumberBoard;

    private boolean isAvaible = true;

    protected String mTechDescription;
    protected List<SignalType> mSupportedSignals;

    // Define How many Control can access the port simultanouesly
     private int mUsableTime;

    public Port(PortType pPortType, int pPinNumberBcm, int pPinNumberBoard, @Nullable List<SignalType> pAcceptedSignals) {
        mType = pPortType;
        mPinNumberBcm = pPinNumberBcm;
        mPinNumberBoard = pPinNumberBoard;
        // TODO [M] getting this value from control manager not work... check again.
        mUsableTime = 1;
        mSupportedSignals = pAcceptedSignals;
        // TODO [W] the usable time shoud be decrement when the port is occupied; do this in control manager.
        updateAvaibility();
    }

    protected abstract void setTechDescription(String pTechDesc);

    protected abstract void setSupportedSignal(List<SignalType> pSupportedSignals);

    private void updateAvaibility() {
        mUsableTime -= 1;
        isAvaible = !(mUsableTime == 0);
    }

    public PortType getType() {
        return mType;
    }

    public void setType(PortType type) {
        mType = type;
    }

    public int getBcmNumber() {
        return mPinNumberBcm;
    }

    public void setBcmNumber(int bcmNumber) {
        mPinNumberBcm = bcmNumber;
    }

    public int getBoardNumber() {
        return mPinNumberBoard;
    }

    public void setBoardNumber(int boardNumber) {
        mPinNumberBoard = boardNumber;
    }

    public List<SignalType> getAcceptedSignals() {
        return mSupportedSignals;
    }

    public void setAcceptedSignals(List<SignalType> acceptedSignals) {
        mSupportedSignals = acceptedSignals;
    }

    public String getName() {
        String result = "";
        if(ControlManager.getInstace().getMode().equals(Mode.BCM)) {
            result = mType.getName() + getBcmNumber();
        } else {
            result = "Pin" + getBoardNumber();
        }
        return result;
    }

    public boolean isAvaible() {
        return isAvaible;
    }


    public String getTechDescription() {
        return mTechDescription;
    }


    public List<SignalType> getSupportedSignals() {
        return mSupportedSignals;
    }

    public int getPinNumber() {
        if(ControlManager.getInstace().getMode().equals(Mode.BCM)) {
            return getBcmNumber();
        } else if(ControlManager.getInstace().getMode().equals(Mode.BOARD)) {
            return getBoardNumber();
        } else {
            // TODO [M] handle Exception
            return -1;
        }
    }

}
