package com.avikenz.ba.picontrol.control;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.avikenz.ba.picontrol.communication.PostHandler;
import com.avikenz.ba.picontrol.control.management.ControlManager;
import com.avikenz.ba.picontrol.control.management.PortType;
import com.avikenz.ba.picontrol.control.param.common.SignalType;

/**
 * Created by AviKenz on 2/11/2018.
 */

public class SwitchControlTest
        extends BaseControl<Switch>
        implements CompoundButton.OnCheckedChangeListener {

    private String mName = this.getClass().getSimpleName();

    private SignalType mSignalType = SignalType.DC;

    public SwitchControlTest(int pPinNumber, String pShortDesc, Context pContext) {
        super(pPinNumber, pShortDesc, pContext);
    }

    @Override
    public void setViewEventListener() {

    }

    @Override
    protected void setupBaseView(Context pContext) {
        mView = new Switch(pContext);
        mView.setOnCheckedChangeListener(this);
    }

    public SignalType getSignalType() {
        return mSignalType;
    }

    @Override
    public ContentValues getRequestParams() {
        ContentValues result = new ContentValues();
        result.put(KEY_NAME, getName());
        result.put(KEY_DIRECTION, direction.getValue());
        // always get mode HERE from Control manager; otherwise is null returned
        result.put(KEY_MODE, mControlManager.getMode().getValue());
        int tempState = mView.isChecked() ? 1 : 0;
        result.put(KEY_STATE, tempState);
        result.put(KEY_SIGNAL_TYPE, mSignalType.getValue());
        result.put(KEY_PIN_NUMBER, mPinNumber);
        result.put(KEY_SHORT_DESC, mShortDescription);
        return result;
    }

    @Override
    public ContentValues getEditableFields() {
        // TODO implement...
        return null;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        new PostHandler(this, ControlManager.getInstace().getServerUrl(), getContext()).execute();
    }

    @Override
    public String getViewDescription() {
        //return "Short description: " + getShortDescription() + " - " + "Pin: " + getPinNumber() + " - " + "SignalType: " + getSignalType().getName();
        return "Short description: " + getShortDescription() + " - " + "Pin: " + getPinNumber() + " - " + "SignalType: " + getSignalType();
    }

    @Override
    public View getView() {
        return getBaseView();
    }

    @Override
    public String getViewType() {
        return Control.SHORT_VIEW_TYP;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    protected Switch getBaseView() {
        return mView;
    }

    @Override
    public PortType getPortType() {
        return mPortype;
    }

    @Override
    public Class getClazz() {
        return getClass();
    }
}
