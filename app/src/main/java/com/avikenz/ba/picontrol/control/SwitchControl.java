package com.avikenz.ba.picontrol.control;

import android.content.ContentValues;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.avikenz.ba.picontrol.communication.PostHandler;
import com.avikenz.ba.picontrol.control.management.ControlManager;
import com.avikenz.ba.picontrol.control.param.common.Mode;
import com.avikenz.ba.picontrol.control.management.PortType;
import com.avikenz.ba.picontrol.control.param.common.SignalType;
import com.avikenz.ba.picontrol.control.param.dc.State;


/**
 * Created by AviKenz on 1/3/2018.
 */

public class SwitchControl
        extends Switch
        implements OutputControl, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = SwitchControl.class.getSimpleName();


    private String mName = "switch_control";
    // Mode must always be get in getPostParam();
    private Mode mMode = null;
    private int mPinNumber = 5;
    private boolean mState = State.OFF.getValue();
    private SignalType mSignalType = SignalType.DC;
    private String mShortDescription = "Short_Description";


    private ControlManager mControlManager;

    public SwitchControl(String pName, int pPinNumber, Context pContext) {
        super(pContext);
        init(pName, pPinNumber, pContext);
    }

    public SwitchControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init("switch_control", 5, context);
    }

    public void init(String pName, int pPinNumber, Context pContext) {
        mControlManager = ControlManager.getInstace();
        mState = isChecked();
        mName = pName;
        mPinNumber = pPinNumber;
        setViewEventListener();
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

    private void setSignalType(SignalType mSignalType) {
        this.mSignalType = mSignalType;
    }

    public void setShortDescription(String mShortDescription) {
        this.mShortDescription = mShortDescription;
    }

    @Override
    public Class getClazz() {
        return getClass();
    }

    @Override
    public ContentValues getEditableFields() {
        ContentValues result = new ContentValues();
        result.put(KEY_NAME, String.class.getName());
        result.put(KEY_PIN_NUMBER, int.class.getName());
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
    public void setViewEventListener() {
        setOnCheckedChangeListener(this);
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
        result.put(KEY_DIRECTION, direction.getValue());
        // always get mode HERE from Control manager; otherwise is null returned
        result.put(KEY_MODE, mControlManager.getMode().getValue());
        result.put(KEY_STATE, tempState);
        result.put(KEY_SIGNAL_TYPE, mSignalType.getValue());
        result.put(KEY_PIN_NUMBER, mPinNumber);
        result.put(KEY_SHORT_DESC, mShortDescription);
        return result;
    }

    @Override
    public PortType getPortType() {
        return PortType.GPIO;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getShortDescription() {
        return null;
    }

    public Mode getMode() {
        return mMode;
    }

    @Override
    public int getPinNumber() {
        return mPinNumber;
    }

    public boolean getState() {
        return mState;
    }

    public SignalType getSignalType() {
        return mSignalType;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mState = isChecked;
        Log.e(TAG, "State: " + mState);
        new PostHandler(this, mControlManager.getServerUrl(), getContext()).execute();
    }

}
