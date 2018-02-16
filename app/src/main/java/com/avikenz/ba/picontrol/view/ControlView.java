package com.avikenz.ba.picontrol.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avikenz.ba.picontrol.R;
import com.avikenz.ba.picontrol.control.Control;
import com.avikenz.ba.picontrol.control.OutputControl;
import com.avikenz.ba.picontrol.control.SwitchControl;

/**
 * Created by AviKenz on 1/10/2018.
 */

public class ControlView extends RelativeLayout {


    private TextView mPortType;
    private TextView mViewDescription;
    private Control mControl;

    private int mTextDescriptionSize;
    private int mNormalViewMargin;

    private Context mContext;

    public ControlView(Control pControl, Context context) {
        super(context);
        init(pControl, context);
    }

    public ControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO [M] declare styleable attr to declare the view in xml; declaration should depend on type of control
        init(new SwitchControl("switch_control", 5, context), context);
    }

    private void init(Control pControl, Context pContext) {
        // init layout
        mContext = pContext;
        mControl = pControl;
        // setup views
        getXmlValues();
        setupPortTypeView();
        setupViewDescriptionView();
        setupControlView();
        // appendToViewGroup views to layout
        addView(mPortType, getPortTypeViewParams());
        addView(mViewDescription, getViewDescriptionParams());
        addView(mControl.getView(), getControlViewParams());
        // set background color
        setBackgroundResource(R.drawable.output_control_background);
    }

    private void setupPortTypeView() {
        mPortType = new TextView(mContext);
        mPortType.setId(R.id.port_type);
        String text = mControl.getPortType() + mControl.getPinNumber();
        mPortType.setText(text);
    }

    private void setupViewDescriptionView() {
        mViewDescription =  new TextView(mContext);
        mViewDescription.setId(R.id.view_description);
        mViewDescription.setText(mControl.getViewDescription());
        mViewDescription.setTextSize(mTextDescriptionSize);
    }

    private void setupControlView() {
        mControl.getView().setId(R.id.control);
    }

    // TODO [I] change view param here instead using set layout param on the view
    private LayoutParams getPortTypeViewParams() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(mNormalViewMargin, mNormalViewMargin, mNormalViewMargin, mNormalViewMargin);
        params.addRule(RelativeLayout.ALIGN_PARENT_START);
        return params;
    }

    private LayoutParams getViewDescriptionParams() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // DO NOT USE ! force the view to fill parent.
        //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.setMargins(mNormalViewMargin, mNormalViewMargin, mNormalViewMargin, mNormalViewMargin);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.BELOW, R.id.control);
        return params;
    }

    private LayoutParams getControlViewParams() {
        int width = LayoutParams.WRAP_CONTENT;
        if(mControl.getViewType() == Control.LONG_VIEW_TYP) {
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

    public TextView getPortType() {
        return mPortType;
    }

    public void setPortType(TextView portType) {
        mPortType = portType;
    }

    public TextView getViewDescription() {
        return mViewDescription;
    }

    public void setViewDescription(TextView viewDescription) {
        mViewDescription = viewDescription;
    }

    public Control getControl() {
        return mControl;
    }

    public void setControl(OutputControl control) {
        mControl = control;
    }

    public void getXmlValues() {
        mNormalViewMargin = (int) mContext.getResources().getDimension(R.dimen.view_normal_margin);
        mTextDescriptionSize = (int) mContext.getResources().getDimension(R.dimen.small_text_size);
    }
}
