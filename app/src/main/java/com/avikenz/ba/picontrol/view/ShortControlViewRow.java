package com.avikenz.ba.picontrol.view;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avikenz.ba.picontrol.control.OutputControl;
import com.avikenz.ba.picontrol.control.SwitchControl;

/**
 * Created by AviKenz on 1/10/2018.
 */

public class ShortControlViewRow extends RelativeLayout {

    private TextView mPortType;
    private TextView mViewDescription;
    private OutputControl mControl;

    private Context mContext;

    public ShortControlViewRow(OutputControl pControl, Context context) {
        super(context);
        init(pControl, context);
    }

    public ShortControlViewRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(new SwitchControl("switch_control", 5, context), context);
    }

    private void init(OutputControl pControl, Context pContext) {
        // init layout
        mContext = pContext;
        mControl = pControl;
        // setup view
        mPortType = new TextView(mContext);
        mPortType.setText(mControl.getPortType());
        mViewDescription =  new TextView(mContext);
        mViewDescription.setText(pControl.getViewDescription());
        // add views to layout
        addView(mPortType, getPortTypeViewParams());
        addView(mViewDescription, getViewDescriptionParams());
        addView(mControl.getView(), getControlViewParams());
        // setup layout params
        //LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // TODO [M] wrap content is not working; check it again
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, 100);
        setLayoutParams(lp);
        // TODO [M] create nice background drawable with color degradation and rounded corners
        setBackgroundColor(Color.parseColor("#DE5E79"));
    }

    private LayoutParams getPortTypeViewParams() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_START);
        return params;
    }

    private LayoutParams getViewDescriptionParams() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        return params;
    }

    private LayoutParams getControlViewParams() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_END);
        return params;
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

    public OutputControl getControl() {
        return mControl;
    }

    public void setControl(OutputControl control) {
        mControl = control;
    }
}
