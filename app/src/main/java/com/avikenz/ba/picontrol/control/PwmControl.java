package com.avikenz.ba.picontrol.control;

import android.content.ContentValues;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

import com.avikenz.ba.picontrol.communication.PostRequestHandler;
import com.avikenz.ba.picontrol.control.management.ControlManager;
import com.avikenz.ba.picontrol.control.param.PwmOutputType;
import com.avikenz.ba.picontrol.control.param.Mode;
import com.avikenz.ba.picontrol.control.management.PortType;
import com.avikenz.ba.picontrol.control.param.SignalType;

/**
 * Created by AviKenz on 1/8/2018.
 */

public class PwmControl
        extends SeekBar
        implements OutputControl, SeekBar.OnSeekBarChangeListener {

    public static String TAG = PwmControl.class.getSimpleName();

    public static int sMaxFrequence = 100;
    public int mMaxDutyCycle;

    private String mName = TAG;
    // Mode must always be get in getPostParam();
    private Mode mMode = null;
    private int mPinNumber = 5;
    private SignalType mSignalType = SignalType.PWM;
    private int mDutyCycle;
    private int mFrequence;
    private PwmOutputType mOutputType;
    private String mShortDescription = "TestPwm";



    private ControlManager mControlManager;

    private String mProgress;

    public PwmControl(String pName, int pPinNumber, int pFrequence, int pDutyCycle, PwmOutputType pOutputType, Context pContext) {
        super(pContext);
        init(TAG, pPinNumber, pFrequence, pDutyCycle, pOutputType, pContext);
    }

    public PwmControl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(String pName, int pPinNumber, int pFrequence, int pDutyCycle, PwmOutputType pOutputType, Context pContext) {
        mControlManager = ControlManager.getInstace();
        mName = pName;
        mPinNumber = pPinNumber;
        mFrequence = pFrequence;
        mDutyCycle = pDutyCycle;
        mOutputType = pOutputType;

        setMax(pOutputType.getmMaxValue());
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

    public void setPinNumber(int pinNumber) {
        mPinNumber = pinNumber;
    }

    public SignalType getSignalType() {
        return mSignalType;
    }

    public void setSignalType(SignalType signalType) {
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

    private String getOuputProgress(int val) {
        String result = "-1";
        if(mOutputType.equals(PwmOutputType.BYTE)) {
            result = Long.toString(val, 2);
        } else {
            result = String.valueOf(val);
        }
        return result;
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
        result.put(KEY_FREQUENCE, Integer.class.getName());
        result.put(KEY_DUTY_CYCLE, Integer.class.getName());
        result.put(KEY_PWM_OUTPUT_TYP, PwmOutputType.class.getName());
        result.put(KEY_SHORT_DESC, String.class.getName());
        return result;
    }

    @Override
    public String getViewType() {
        return Control.LONG_VIEW_TYP;
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public String getPortType() {
        return PortType.GPIO.getName();
    }

    @Override
    public int getPinNumber() {
        return mPinNumber;
    }

    @Override
    public String getViewDescription() {
        return "Short description: " + getShortDescription() + " - " + "Pin: " + getPinNumber() + " - " + "SignalType: " + getSignalType().getName();
    }

    @Override
    public ContentValues getRequestParams() {
        ContentValues result = new ContentValues();
        result.put(KEY_NAME, getName());
        result.put(KEY_DIRECTION, direction.getValue());
        // always get mode HERE from Control manager; otherwise is null returned
        result.put(KEY_MODE, mControlManager.getMode().getValue());
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
    public void setChangeListener() {
        setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mProgress = "0";
        new PostRequestHandler(this, getContext()).execute();

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mProgress = getOuputProgress(getProgress());
        new PostRequestHandler(this, getContext()).execute();
    }
}
