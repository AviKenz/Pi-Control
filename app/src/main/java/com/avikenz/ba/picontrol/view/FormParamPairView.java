package com.avikenz.ba.picontrol.view;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

/**
 * Created by AviKenz on 1/12/2018.
 */

public class FormParamPairView extends LinearLayout {

    private Map.Entry<String, Object> mPair;

    private TextView mKey;
    private EditText mValue;

    private LayoutParams mChildrenParams;

    public static final int WEIGHT = 1;

    private Context mContext;

    public FormParamPairView(Map.Entry<String, Object> pPair, Context context) {
        super(context);
        init(pPair, context);
    }

    // TODO [W] NOT WORKING YET; view cant be create yet using xml
    public FormParamPairView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(Map.Entry<String, Object> pPair, Context pContext) {
        mContext = pContext;
        mPair = pPair;
        setOrientation(HORIZONTAL);
        setLayoutParams();
        setupChildrenParam();
        setupKeyView();
        setupValueView();
        addView(mKey, mChildrenParams);
        addView(mValue, mChildrenParams);
    }

    private void setupKeyView() {
        mKey = new TextView(mContext);
        mKey.setText(mPair.getKey());
    }
    // TODO [N] implement value logik; choice other plain text;
    private void setupValueView() {
        mValue = new EditText(mContext);
        mValue.setGravity(Gravity.RIGHT);
        mValue.setText(mPair.getValue().toString());
    }

    private void setupChildrenParam() {
        mChildrenParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mChildrenParams.weight = WEIGHT;
    }

    private void setLayoutParams() {
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }
}
