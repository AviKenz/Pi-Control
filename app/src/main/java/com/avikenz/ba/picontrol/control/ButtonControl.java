package com.avikenz.ba.picontrol.control;

import android.content.ContentValues;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.avikenz.ba.picontrol.communication.PostRequestHandler;
import com.avikenz.ba.picontrol.control.management.ControlManager;
import com.avikenz.ba.picontrol.control.param.Direction;
import com.avikenz.ba.picontrol.control.param.Mode;
import com.avikenz.ba.picontrol.control.management.PortType;
import com.avikenz.ba.picontrol.control.param.SignalType;
import com.avikenz.ba.picontrol.control.param.State;

/**
 * Created by AviKenz on 1/6/2018.
 */

public class ButtonControl
        extends Button
        implements OutputControl,  View.OnTouchListener {

    public static final String TAG = ButtonControl.class.getSimpleName();

    private String mName = TAG;
    // Mode must always be get in getPostParam();
    private Mode mMode = null;
    private int mPinNumber = 5;
    private boolean mState = State.OFF.getValue();
    private SignalType mSignalType = SignalType.DC;
    private String mShortDescription = "TestButton";
    private String mServerUrl;

    private ControlManager mControlManager;

    public ButtonControl(String pName, int pPinNumber, Context pContext) {
        super(pContext);
        init(TAG, pPinNumber, pContext);
    }

    public ButtonControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(String pName, int pPinNumber, Context pContext) {
        mControlManager = ControlManager.getInstace();
        mName = pName;
        mPinNumber = pPinNumber;
        setText("ON-OFF");
        setChangeListener();
    }

    @Override
    public Class getClazz() {
        return getClass();
    }

    @Override
    public ContentValues getEditableAttributes() {
        ContentValues result = new ContentValues();
        result.put(KEY_NAME, String.class.getName());
        result.put(KEY_PIN_NUMBER, Integer.class.getName());
        result.put(KEY_SHORT_DESC, String.class.getName());
        return result;
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
        return "Short description: " + getShortDescription() + " - " + "Pin: " + getPinNumber() + " - " + "SignalType: " + getSignalType().getName();
    }

    @Override
    public ContentValues getRequestParams() {
        ContentValues result = new ContentValues();
        int tempState = mState ? 1 : 0;
        result.put(KEY_NAME, getName());
        result.put(KEY_DIRECTION, Direction.OUT.getValue());
        // always get mode HERE from Control manager; otherwise is null returned
        result.put(KEY_MODE, mControlManager.getMode().getValue());
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
        new PostRequestHandler(this, getContext()).execute();
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
