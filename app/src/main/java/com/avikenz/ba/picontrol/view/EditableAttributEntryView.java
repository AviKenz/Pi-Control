package com.avikenz.ba.picontrol.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.avikenz.ba.picontrol.control.Control;
import com.avikenz.ba.picontrol.control.management.ControlManager;
import com.avikenz.ba.picontrol.control.param.PwmOutputType;
import com.avikenz.ba.picontrol.control.param.Mode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by AviKenz on 1/12/2018.
 */

public class EditableAttributEntryView
        extends LinearLayout {

    private final String TAG = getClass().getSimpleName();

    public static final String TYP_ENUM = "enum";
    public static final String TYP_INT = "int";
    public static final String TYP_STRING = "string";

    private Map.Entry<String, Object> mPair;

    private TextView mKey;
    private View mValue;

    private LayoutParams mChildrenParams;

    public static final int WEIGHT = 1;

    private Context mContext;

    public EditableAttributEntryView(Map.Entry<String, Object> pPair, Context context) {
        super(context);
        init(pPair, context);
    }

    // TODO [W] NOT WORKING YET; view cant be create yet using xml
    public EditableAttributEntryView(Context context, @Nullable AttributeSet attrs) {
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
        // Capitalize first letter and remove underscore character
        String val = new String(mPair.getKey());
        // TODO [M] doest work; String are immutable
        //val = val.replace("_", " ");
        //val = val.substring(0, 1).toUpperCase() + val.substring(1);
        mKey.setText(val);
    }

    private void setupValueView() {
        String val = mPair.getValue().toString();
        // generate Spinner for pin selection; the spinner show only available pins;
        // the available pins depend on the pinUsableTime in Port class
        if(mPair.getKey().equals(Control.KEY_PIN_NUMBER)) {
            mValue = new Spinner(mContext);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,
                    android.R.layout.simple_spinner_dropdown_item,
                    ControlManager.getInstace().getFreeGpioPortNumberList());
            Spinner v = (Spinner) mValue;
            v.setAdapter(adapter);
        }
        // generate spinner when value equals FQN of Enum
        else if (val.equals(Enum.class.getName())) {
            mValue = new Spinner(mContext);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext,
                    android.R.layout.simple_spinner_dropdown_item, getEnumValues(val));
            Spinner v = (Spinner) mValue;
            v.setAdapter(adapter);
        // generate spinner for pwm output type; the output of pwm can be an integer or 8 bit value
        } else if(mPair.getValue().toString().equals(PwmOutputType.class.getName())) {
            mValue = new Spinner(mContext);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item,
                    getEnumValues(PwmOutputType.class.getName()));
            Spinner v = (Spinner) mValue;
            v.setAdapter(adapter);
        // generate spinner for numbering mode (used in control manager).
        } else if(mPair.getValue().toString().equals(Mode.class.getName())) {
            mValue = new Spinner(mContext);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item,
                    getEnumValues(Mode.class.getName()));
            Spinner v = (Spinner) mValue;
            v.setAdapter(adapter);
        // default; generate an edit text
        } else {
            mValue = new EditText(mContext);
            EditText v = (EditText) mValue;
            // set input type
            if(!val.equals(String.class.getName())) {
                v.setInputType(InputType.TYPE_CLASS_NUMBER);
            }
            v.setGravity(Gravity.RIGHT);
        }
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
        return result;
    }

    private void setupChildrenParam() {
        mChildrenParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mChildrenParams.weight = WEIGHT;
    }

    private void setLayoutParams() {
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    public String getKey() {
        return mKey.getText().toString();
    }

    public String getValue() {
        if(mValue instanceof EditText) {
            return ((EditText) mValue).getText().toString();
        } else {
            return ((Spinner) mValue).getSelectedItem().toString();
        }
    }
}

