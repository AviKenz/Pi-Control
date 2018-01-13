package com.avikenz.ba.picontrol.view;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avikenz.ba.picontrol.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by AviKenz on 1/12/2018.
 */

public class FormParamPairView extends LinearLayout {

    private final String TAG = getClass().getSimpleName();

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

    private void setupValueView() {
        mValue = new EditText(mContext);
        mValue.setGravity(Gravity.RIGHT);
        String val = mPair.getValue().toString();
        if ( isEnum(val) ) {
            Log.d(TAG, getEnumValues(val).toString());
            mValue.setOnFocusChangeListener(new OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        Log.d(TAG, "focused");
                        // TODO [N] implement Dialog to Show Enum;
                    }
                }
            });
        } else {
            mValue.setText(isEnum(val) + "");
        }
    }

    private boolean isEnum(String pFullName) {
        return pFullName.contains("avikenz");
    }

    private List<String> getEnumValues(String pName) {
        List<String> result = new ArrayList<>();
        try {
            Class clazz = Class.forName(pName);
            Field[] fields = clazz.getFields();
            for(Field f : fields) {
                result.add(f.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO [I] would probabily need the enum class to instantiate it and pass to controller
        return result;
    }

    private void setupChildrenParam() {
        mChildrenParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mChildrenParams.weight = WEIGHT;
    }

    private void setLayoutParams() {
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

}
