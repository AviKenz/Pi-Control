package com.avikenz.ba.picontrol.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avikenz.ba.picontrol.control.Control;
import com.avikenz.ba.picontrol.control.OutputControl;
import com.avikenz.ba.picontrol.control.SwitchControl;

/**
 * Created by AviKenz on 1/10/2018.
 */

public class ControlViewRow extends RelativeLayout {

    private TextView mPortType;
    private TextView mViewDescription;
    private Control mControl;

    private int mTextDescriptionSize = 10;

    private Context mContext;

    public ControlViewRow(OutputControl pControl, Context context) {
        super(context);
        init(pControl, context);
    }

    public ControlViewRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(new SwitchControl("switch_control", 5, context), context);
    }

    private void init(OutputControl pControl, Context pContext) {
        // init layout
        mContext = pContext;
        mControl = pControl;
        // setup view
        mPortType = new TextView(mContext);
        mPortType.setText(mControl.getPortType() + mControl.getPinNumber());
        mViewDescription =  new TextView(mContext);
        mViewDescription.setText(pControl.getViewDescription());
        mViewDescription.setTextSize(mTextDescriptionSize);
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


    // TODO [I] change view param here instead using set layout param on the view
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
        LayoutParams params = new LayoutParams(300, LayoutParams.WRAP_CONTENT);
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

    public Control getControl() {
        return mControl;
    }

    public void setControl(OutputControl control) {
        mControl = control;
    }
}
