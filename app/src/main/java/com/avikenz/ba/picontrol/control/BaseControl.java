package com.avikenz.ba.picontrol.control;

import android.content.Context;
import android.view.View;

import com.avikenz.ba.picontrol.control.management.ControlManager;
import com.avikenz.ba.picontrol.control.management.PortType;

/**
 * Created by AviKenz on 2/11/2018.
 */

public abstract class BaseControl<T extends View> implements OutputControl{

    protected int mPinNumber;
    protected String mShortDescription;

    protected Context mContext;


    protected T mView;

    protected PortType mPortype = PortType.GPIO;
    protected ControlManager mControlManager = ControlManager.getInstace();

    private String mServerUrl;

    public BaseControl(int pPinNumber, String pShortDesc, Context pContext) {
        init(pPinNumber, pShortDesc, pContext);
    }

    private void init(int pPinNumber, String pShortDesc, Context pContext) {
        mPinNumber = pPinNumber;
        mShortDescription = pShortDesc;
        mContext = pContext;
        mServerUrl = ControlManager.getInstace().getServerUrl();
    }

    @Override
    public int getPinNumber() {
        return mPinNumber;
    }

    @Override
    public String getShortDescription() {
        return mShortDescription;
    }

    public Context getContext() {
        return mContext;
    }

    public abstract T getBaseView();
}
