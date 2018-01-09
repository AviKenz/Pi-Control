package com.avikenz.ba.picontrol.control;

import android.content.ContentValues;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

import com.avikenz.ba.picontrol.communication.PostHandler;
import com.avikenz.ba.picontrol.control.manager.ControlManager;
import com.avikenz.ba.picontrol.control.param.common.Mode;
import com.avikenz.ba.picontrol.control.param.common.Type;

/**
 * Created by AviKenz on 1/8/2018.
 */

public class PwmControl
        extends SeekBar
        implements OutputControl, SeekBar.OnSeekBarChangeListener {

    public static String TAG = PwmControl.class.getSimpleName();

    public static int sMaxFrequence = 100;
    public static int smaxDutyCycle = 100;

    private String mName = "button_control";
    private Mode mMode;
    private int mPinNumber = 5;
    private Type mSignalType = Type.PWM;
    private int mDutyCycle;
    private int mFrequence;
    private String mShortDescription = "Short_Description";



    private ControlManager mControlManager;

    private int mProgress;

    public PwmControl(String pName, int pPinNumber, int pFrequence, int pDutyCycle, Context pContext) {
        super(pContext);
        init(pName, pPinNumber, pFrequence, pDutyCycle, pContext);
    }

    public PwmControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init("seek_bar", 5, 1000, 20, context);
    }

    private void init(String pName, int pPinNumber, int pFrequence, int pDutyCycle, Context pContext) {
        mControlManager = getControlManager();
        mName = pName;
        mMode = mControlManager.getMode();
        mMode = Mode.BCM;
        mPinNumber = pPinNumber;
        mFrequence = pFrequence;
        mDutyCycle = pDutyCycle;

        setMax(smaxDutyCycle);
        setChangeListener();
    }

    // TODO [M] use setters and getters in init and check value before assignment (IN ALL CONTROL !!!!!)
    public void setName(String name) {
        mName = name;
    }

    public Mode getMode() {
        return mMode;
    }

    public void setMode(Mode mode) {
        mMode = mode;
    }

    public int getPinNumber() {
        return mPinNumber;
    }

    public void setPinNumber(int pinNumber) {
        mPinNumber = pinNumber;
    }

    public Type getSignalType() {
        return mSignalType;
    }

    public void setSignalType(Type signalType) {
        mSignalType = signalType;
    }

    public int getDutyCycle() {
        return mDutyCycle;
    }

    public void setDutyCycle(int dutyCycle) {
        mDutyCycle = dutyCycle;
    }

    public int getFrequence() {
        return mFrequence;
    }

    public void setFrequence(int frequence) {
        mFrequence = frequence;
    }

    public void setShortDescription(String shortDescription) {
        mShortDescription = shortDescription;
    }

    @Override
    public ControlManager getControlManager() {
        return (ControlManager) getContext().getApplicationContext();
    }

    @Override
    public void updateControlManager(ControlManager pManager) {

    }

    @Override
    public ContentValues getPostParams() {
        Log.d(TAG, "getPostParams()");
        ContentValues result = new ContentValues();
        result.put(KEY_NAME, getName());
        result.put(KEY_DIRECTION, direction.getValue());
        result.put(KEY_MODE, mMode.getValue());
        result.put(KEY_SIGNAL_TYPE, mSignalType.getValue());
        result.put(KEY_PIN_NUMBER, mPinNumber);
        result.put(KEY_FREQUENCE, mFrequence);
        result.put(KEY_DUTY_CYCLE, mProgress);
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
        return mControlManager.getServerUrl();
    }

    @Override
    public void setChangeListener() {
        setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.e(TAG, "changed");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.e(TAG, "start");
        mProgress = 0;
        new PostHandler(this, getServerUrl(), getContext()).execute();

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.e(TAG, "stop");
        mProgress = getProgress();
        new PostHandler(this, mControlManager.getServerUrl(), getContext()).execute();
    }
}
