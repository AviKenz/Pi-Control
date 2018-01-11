package com.avikenz.ba.picontrol.control.manager;

import android.support.annotation.Nullable;

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

    // TODO [H] implement PortType Enum Type
    Port(PortType pPortType, int pPinNumberBcm, int pPinNumberBoard, @Nullable List<SignalType> pAcceptedSignals) {
        mType = pPortType;
        mPinNumberBcm = pPinNumberBcm;
        mPinNumberBoard = pPinNumberBoard;
        mAcceptedSignals = pAcceptedSignals;
        belegPort();
        updateAvaibility();
    };

    private void belegPort() {
        mUsableTime -= 1;
    }

    private boolean updateAvaibility() {
        return !(mUsableTime == 0);
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

    public int getUsableTime() {
        return mUsableTime;
    }

    private void setUsableTime(int usableTime) {
        mUsableTime = ControlManager.sPortsUsableTime;
    }

    public String getName() {
        String result = "";
        return result;
    }
}
