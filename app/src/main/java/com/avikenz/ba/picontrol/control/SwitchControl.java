package com.avikenz.ba.picontrol.control;

import android.content.ContentValues;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.avikenz.ba.picontrol.communication.PostHandler;
import com.avikenz.ba.picontrol.control.manager.ControlManager;
import com.avikenz.ba.picontrol.control.param.common.Direction;
import com.avikenz.ba.picontrol.control.param.common.Mode;
import com.avikenz.ba.picontrol.control.param.common.Type;
import com.avikenz.ba.picontrol.control.param.dc.State;


/**
 * Created by AviKenz on 1/3/2018.
 */

public class SwitchControl
        extends Switch
        implements OutputControl, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = SwitchControl.class.getSimpleName();


    private String mName = "switch_control";
    private Mode mMode = Mode.BCM;
    private int mPinNumber = 5;
    private boolean mState = State.OFF.getValue();
    private Type mSignalType = Type.DC;
    private String mShortDescription = "Short_Description";
    private String mServerUrl;

    private Context mContext;
    private ControlManager mControlManager;

    public SwitchControl(String pName, int pPinNumber, Context pContext) {
        super(pContext);
        init(pName, pPinNumber, pContext);
    }

    public SwitchControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO [M] declare styleable attr for the view in xml to get this params
        init("switch_control", 5, context);
    }

    public void init(String pName, int pPinNumber, Context pContext) {
        mState = isChecked();
        mName = pName;
        //mMode = mControlManager.getMode();
        mMode = Mode.BCM;
        mPinNumber = pPinNumber;
        mContext = pContext;
        mControlManager = getControlManager();
        setChangeListener();
    }

    @Override
    public void setChangeListener() {
        setOnCheckedChangeListener(this);
    }

    @Override
    public ContentValues getPostParams() {
        ContentValues result = new ContentValues();
        int tempState = mState ? 1 : 0;
        result.put(KEY_NAME, "dc_output");
        result.put(KEY_DIRECTION, direction.getValue());
        result.put(KEY_MODE, mMode.getValue());
        result.put(KEY_STATE, tempState);
        result.put(KEY_SIGNAL_TYPE, mSignalType.getValue());
        result.put(KEY_PIN_NUMBER, mPinNumber);
        result.put(KEY_SHORT_DESC, mShortDescription);
        return result;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setMode(Mode mMode) {
        this.mMode = mMode;
    }

    public void setPinNumber(int mPinNumber) {
        this.mPinNumber = mPinNumber;
    }

    public void setState(boolean mState) {
        this.mState = mState;
    }

    private void setSignalType(Type mSignalType) {
        this.mSignalType = mSignalType;
    }

    public void setShortDescription(String mShortDescription) {
        this.mShortDescription = mShortDescription;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getShortDescription() {
        return null;
    }

    @Override
    public String getServerUrl() {
        if(mServerUrl == null) {
            // TODO [M] handle error
        }
        return mServerUrl;
    }

    public Mode getMode() {
        return mMode;
    }

    public int getPinNumber() {
        return mPinNumber;
    }

    public boolean getState() {
        return mState;
    }

    public Type getSignalType() {
        return mSignalType;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mState = isChecked;
        // TODO [M] pass server url to controler class
        Log.e(TAG, "State: " + mState);
        new PostHandler(this, "192.168.1.101", mContext).execute();
    }

    @Override
    public ControlManager getControlManager() {
        return (ControlManager) mContext.getApplicationContext();
    }

    @Override
    public void updateControlManager(ControlManager pManager) {

    }
}
