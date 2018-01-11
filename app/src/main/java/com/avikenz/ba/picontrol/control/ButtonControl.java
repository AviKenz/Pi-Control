package com.avikenz.ba.picontrol.control;

import android.content.ContentValues;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.avikenz.ba.picontrol.communication.PostHandler;
import com.avikenz.ba.picontrol.control.manager.ControlManager;
import com.avikenz.ba.picontrol.control.param.common.Direction;
import com.avikenz.ba.picontrol.control.param.common.Mode;
import com.avikenz.ba.picontrol.control.manager.PortType;
import com.avikenz.ba.picontrol.control.param.common.SignalType;
import com.avikenz.ba.picontrol.control.param.dc.State;

/**
 * Created by AviKenz on 1/6/2018.
 */

public class ButtonControl
        extends Button
        implements OutputControl, View.OnTouchListener {

    public static final String TAG = ButtonControl.class.getSimpleName();

    private String mName = "button_control";
    private Mode mMode = null;
    private int mPinNumber = 5;
    private boolean mState = State.OFF.getValue();
    private SignalType mSignalType = SignalType.DC;
    private String mShortDescription = "Short_Description";
    private String mServerUrl;

    private ControlManager mControlManager;

    public ButtonControl(String pName, int pPinNumber, Context pContext) {
        super(pContext);
        init(pName, pPinNumber, pContext);
    }

    public ButtonControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO [M] declare styleable attr for the view in xml to get this params
        init("button_control", 5, context);
    }

    private void init(String pName, int pPinNumber, Context pContext) {
        mControlManager = (ControlManager) pContext.getApplicationContext();
        mName = pName;
        mMode = mControlManager.getMode();
        mPinNumber = pPinNumber;
        setText(getName());
        setChangeListener();
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public String getViewType() {
        return Control.SHORT_VIEW_TYP;
    }

    @Override
    public String getPortType() {
        return PortType.GPIO.getName();
    }

    @Override
    public String getViewDescription() {
        return "Short description: " + getShortDescription() + " - " + "Pin: " + getPinNumber() + " - " + "Signal SignalType: " + getSignalType().getValue();
    }

    @Override
    public ContentValues getPostParams() {
        ContentValues result = new ContentValues();
        int tempState = mState ? 1 : 0;
        result.put(KEY_NAME, "button_control");
        result.put(KEY_DIRECTION, Direction.OUT.getValue());
        result.put(KEY_MODE, mMode.getValue());
        result.put(KEY_STATE, tempState);
        result.put(KEY_SIGNAL_TYPE, mSignalType.getValue());
        result.put(KEY_PIN_NUMBER, mPinNumber);
        result.put(KEY_SHORT_DESC, mShortDescription);
        return result;
    }



    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getShortDescription() {
        return mShortDescription;
    }

    @Override
    public void setChangeListener() {
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mState = State.ON.getValue();
                Log.d(TAG, "TOUCHED");
                break;
            case MotionEvent.ACTION_UP:
                mState = State.OFF.getValue();
                Log.d(TAG, "RELEASED");
                break;
        }
        new PostHandler(this, mControlManager.getServerUrl(), getContext()).execute();
        return true;
    }

    public void setName(String name) {
        mName = name;
    }

    public Mode getMode() {
        return mMode;
    }

    public void setMode(Mode mode) {
        mMode = mode;
    }

    @Override
    public int getPinNumber() {
        return mPinNumber;
    }

    public void setPinNumber(int pinNumber) {
        mPinNumber = pinNumber;
    }

    public boolean getState() {
        return mState;
    }

    public void setState(boolean state) {
        mState = state;
    }

    public SignalType getSignalType() {
        return mSignalType;
    }

    public void setSignalType(SignalType signalType) {
        mSignalType = signalType;
    }


    public void setShortDescription(String shortDescription) {
        mShortDescription = shortDescription;
    }
}
