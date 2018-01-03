package com.avikenz.ba.picontrol.control;

import android.graphics.Path;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.avikenz.ba.picontrol.control.param.common.Direction;
import com.avikenz.ba.picontrol.control.param.common.Mode;

/**
 * Created by AviKenz on 1/3/2018.
 */

public abstract class Control  {

    private String mName;
    protected Mode mMode;
    protected int mPinNumber;
    protected Direction mDirection;
    protected String mDescription;

    protected abstract void setDirection(Direction pDirection);

    protected abstract void setDescription(String pDescription);

    private Control(String pName) {
        mName = pName;
    }

    protected Control(String pName, Mode pMode, int pPinNumber) {
        this(pName);
        mMode = pMode;
        mPinNumber = pPinNumber;
    }

    public String getName() {
        return mName;
    }

    public Mode getMode() {
        return mMode;
    }

    public int getPinNumber() {
        return mPinNumber;
    }

    public Direction getDirection() {
        return mDirection;
    }

    public String getDescription() {
        return mDescription;
    }
}
