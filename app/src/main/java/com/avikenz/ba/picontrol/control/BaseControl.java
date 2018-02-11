package com.avikenz.ba.picontrol.control;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avikenz.ba.picontrol.R;
import com.avikenz.ba.picontrol.control.management.ControlManager;
import com.avikenz.ba.picontrol.control.management.PortType;

/**
 * Created by AviKenz on 2/11/2018.
 */

public abstract class BaseControl<T extends View>
        extends RelativeLayout
        implements OutputControl{

    protected int mPinNumber;
    protected String mShortDescription;

    protected Context mContext;


    protected T mView;

    private TextView mPortType;
    private TextView mViewDescription;

    private int mTextDescriptionSize;
    private int mNormalViewMargin;

    protected PortType mPortype = PortType.GPIO;
    protected ControlManager mControlManager = ControlManager.getInstace();


    public BaseControl(int pPinNumber, String pShortDesc, Context pContext) {
        super(pContext);
        init(pPinNumber, pShortDesc, pContext);
    }

    private void init(int pPinNumber, String pShortDesc, Context pContext) {
        mPinNumber = pPinNumber;
        mShortDescription = pShortDesc;
        mContext = pContext;
        setupBaseView(pContext);
        //
        // init layout
        // setup views
        getXmlValues();
        setupPortTypeView();
        setupViewDescriptionView();
        // appendToViewGroup views to layout
        addView(mPortType, generatePortTypeViewParams());
        addView(mViewDescription, generateViewDescriptionParams());
        addView(mView, generateControlViewParams());
        // set background color
        setBackgroundResource(R.drawable.output_control_background);
    }

    @Override
    public int getPinNumber() {
        return mPinNumber;
    }

    @Override
    public String getShortDescription() {
        return mShortDescription;
    }

    protected abstract T getBaseView();

    protected abstract void setupBaseView(Context pContext);

    private void setupPortTypeView() {
        mPortType = new TextView(mContext);
        mPortType.setId(R.id.port_type);
        String text = getPortType().getName() + getPinNumber();
        mPortType.setText(text);
    }

    private void setupViewDescriptionView() {
        mViewDescription =  new TextView(mContext);
        mViewDescription.setId(R.id.view_description);
        mViewDescription.setText(getViewDescription());
        mViewDescription.setTextSize(mTextDescriptionSize);
    }


    // TODO [I] change view param here instead using set layout param on the view
    private LayoutParams generatePortTypeViewParams() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(mNormalViewMargin, mNormalViewMargin, mNormalViewMargin, mNormalViewMargin);
        params.addRule(RelativeLayout.ALIGN_PARENT_START);
        return params;
    }

    private LayoutParams generateViewDescriptionParams() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // DO NOT USE ! force the view to fill parent.
        //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.setMargins(mNormalViewMargin, mNormalViewMargin, mNormalViewMargin, mNormalViewMargin);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.BELOW, R.id.control);
        return params;
    }

    private LayoutParams generateControlViewParams() {
        int width = LayoutParams.WRAP_CONTENT;
        if(getViewType() == Control.LONG_VIEW_TYP) {
            width = dpToPx(200);
        }
        LayoutParams params = new LayoutParams(width, LayoutParams.WRAP_CONTENT);
        params.setMargins(mNormalViewMargin, mNormalViewMargin, mNormalViewMargin, mNormalViewMargin);
        params.addRule(RelativeLayout.ALIGN_PARENT_END);
        return params;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }



    public void setPortType(TextView portType) {
        mPortType = portType;
    }


    public void setViewDescription(TextView viewDescription) {
        mViewDescription = viewDescription;
    }


    public void getXmlValues() {
        mNormalViewMargin = (int) mContext.getResources().getDimension(R.dimen.view_normal_margin);
        mTextDescriptionSize = (int) mContext.getResources().getDimension(R.dimen.small_text_size);
    }
}
