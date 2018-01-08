package com.avikenz.ba.picontrol.control;

import android.content.ContentValues;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;

import com.avikenz.ba.picontrol.communication.PostHandler;
import com.avikenz.ba.picontrol.control.manager.ControlManager;
import com.avikenz.ba.picontrol.control.param.common.Mode;
import com.avikenz.ba.picontrol.control.param.common.Type;
import com.avikenz.ba.picontrol.control.param.dc.State;

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
    private String mServerUrl;

    private int mCurrentDutyCycle;

    private Context mContext;
    private ControlManager mControlManager;

    public PwmControl(String pName, int pPinNumber, int pFrequence, int pDutyCycle, Context pContext) {
        super(pContext);
        init(pName, pPinNumber, pFrequence, pDutyCycle, pContext);
    }

    public PwmControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init("seek_bar", 5, 1000, 20, context);
    }

    private void init(String pName, int pPinNumber, int pFrequence, int pDutyCycle, Context pContext) {
        mName = pName;
        //mMode = mControlManager.getMode();
        mMode = Mode.BCM;
        mPinNumber = pPinNumber;
        mFrequence = pFrequence;
        mDutyCycle = pDutyCycle;
        mContext = pContext;
        mControlManager = getControlManager();

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
        return (ControlManager) mContext.getApplicationContext();
    }

    @Override
    public void updateControlManager(ControlManager pManager) {

    }

    @Override
    public ContentValues getPostParams() {
        ContentValues result = new ContentValues();
        result.put(KEY_NAME, "pwm_output");
        result.put(KEY_DIRECTION, direction.getValue());
        result.put(KEY_MODE, mMode.getValue());
        result.put(KEY_SIGNAL_TYPE, mSignalType.getValue());
        result.put(KEY_PIN_NUMBER, mPinNumber);
        result.put(KEY_FREQUENCE, mFrequence);
        result.put(KEY_DUTY_CYCLE, mDutyCycle);
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
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.d(TAG, "Stopped");
        mCurrentDutyCycle = seekBar.getProgress();
        new PostHandler(this, mControlManager.getServerUrl(), mContext).execute();
    }
}
