package com.avikenz.ba.picontrol.control.management;

import android.support.annotation.Nullable;

import com.avikenz.ba.picontrol.control.param.common.Mode;
import com.avikenz.ba.picontrol.control.param.common.SignalType;

import java.util.List;

/**
 * Created by AviKenz on 1/11/2018.
 * represent a port on the raspberry pi.
 */

public class Port {

    private PortType mType;
    private int mPinNumberBcm;
    private int mPinNumberBoard;
    private List<SignalType> mAcceptedSignals;

    private boolean isAvaible = true;

    // Define How many Control can access the port simultanouesly
     private int mUsableTime;

    public Port(PortType pPortType, int pPinNumberBcm, int pPinNumberBoard, @Nullable List<SignalType> pAcceptedSignals) {
        mType = pPortType;
        mPinNumberBcm = pPinNumberBcm;
        mPinNumberBoard = pPinNumberBoard;
        mAcceptedSignals = pAcceptedSignals;
        // TODO [W] the usable time shoud be decrement when the port is occupied; do this in control manager.
        //setUsableTime();
        updateAvaibility();
    }

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
        return mAcceptedSignals;
    }

    public void setAcceptedSignals(List<SignalType> acceptedSignals) {
        mAcceptedSignals = acceptedSignals;
    }

    public int setUsableTime() {
        return mUsableTime;
    }

    private void setUsableTime(int usableTime) {
        mUsableTime = ControlManager.sPortsUsableTime;
    }

    public String getName() {
        String result = "";
        if(ControlManager.getMode().equals(Mode.BCM)) {
            result = mType.getName() + getBcmNumber();
        } else {
            result = "Pin" + getBoardNumber();
        }
        return result;
    }


    public boolean isAvaible() {
        return isAvaible;
    }
}
