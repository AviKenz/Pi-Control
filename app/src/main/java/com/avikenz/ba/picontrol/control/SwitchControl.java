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
import com.avikenz.ba.picontrol.control.param.common.Direction;
import com.avikenz.ba.picontrol.control.param.common.Mode;
import com.avikenz.ba.picontrol.control.param.common.Type;
import com.avikenz.ba.picontrol.control.param.dc.State;


/**
 * Created by AviKenz on 1/3/2018.
 */

public class SwitchControl extends Switch implements OutputControl, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = SwitchControl.class.getSimpleName();

    public static String KEY_NAME = "name";
    public static String KEY_MODE = "mode";
    public static String KEY_DIRECTION = "direction";
    public static String KEY_STATE = "state";
    public static String KEY_SIGNAL_TYPE = "signal_type";
    public static String KEY_PIN_NUMBER = "pin_number";
    public static String KEY_SHORT_DESC = "short_description";

    private String mName = "switch_control";
    private Mode mMode = Mode.BCM;
    private int mPinNumber = 5;
    private boolean mState = State.OFF.getValue();
    private Type mSignalType = Type.DC;
    private String mShortDescription = "Short_Description";
    private String mServerUrl;

    private Context mContext;

    public SwitchControl(String pName, Mode pMode, int pPinNumber, Context pContext) {
        super(pContext);
        init(pName, pMode, pPinNumber, pContext);
        setChangeListener();
    }

    public SwitchControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        setChangeListener();
    }

    public void init(String pName, Mode pMode, int pPinNumber, Context pContext) {
        mState = isChecked();
        mName = pName;
        mMode = pMode;
        mPinNumber = pPinNumber;
        mContext = pContext;
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
        result.put(KEY_DIRECTION, Direction.OUT.getValue());
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
        new PostHandler(this, "192.168.1.25", mContext).execute();
    }
}
