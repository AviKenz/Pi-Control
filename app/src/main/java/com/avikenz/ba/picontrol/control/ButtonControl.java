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
import com.avikenz.ba.picontrol.control.manager.ControlManagerInterface;
import com.avikenz.ba.picontrol.control.param.common.Direction;
import com.avikenz.ba.picontrol.control.param.common.Mode;
import com.avikenz.ba.picontrol.control.param.common.Type;
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
    private Type mSignalType = Type.DC;
    private String mShortDescription = "Short_Description";
    private String mServerUrl;

    private ControlManager mControlManager;

    public ButtonControl(String pName, int pPinNumber, Context pContext) {
        super(pContext);
        init(pName, pPinNumber);
    }

    public ButtonControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO [M] declare styleable attr for the view in xml to get this params
        init("button_control", 5);
    }

    private void init(String pName, int pPinNumber) {
        mControlManager = getControlManager();
        mName = pName;
        mMode = mControlManager.getMode();
        mPinNumber = pPinNumber;
        setText(getName());
        setChangeListener();
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
    public String getServerUrl() {
        return mServerUrl;
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

    @Override
    public ControlManager getControlManager() {
        return (ControlManager) getContext().getApplicationContext();
    }

    @Override
    public void updateControlManager(ControlManager pManager) {

    }
}
