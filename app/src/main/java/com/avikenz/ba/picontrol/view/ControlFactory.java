package com.avikenz.ba.picontrol.view;

import android.content.ContentValues;
import android.content.Context;

import com.avikenz.ba.picontrol.Exception.InvalidParameterSetException;
import com.avikenz.ba.picontrol.control.ButtonControl;
import com.avikenz.ba.picontrol.control.Control;
import com.avikenz.ba.picontrol.control.OutputControl;
import com.avikenz.ba.picontrol.control.PwmControl;
import com.avikenz.ba.picontrol.control.SwitchControl;
import com.avikenz.ba.picontrol.control.management.ControlManager;
import com.avikenz.ba.picontrol.control.param.PwmOutputType;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by AviKenz on 1/15/2018.
 */

public class ControlFactory {

    private String mClName;
    private Control mControl;
    private ContentValues mParam;
    private Context mContext;

    private ControlManager mControlManager;

    public ControlFactory(Control pControl, ContentValues pParam, Context pContext) {
        mControlManager = ControlManager.getInstace();
        mClName = pControl.getClass().getSimpleName();
        mControl = pControl;
        mParam = pParam;
        mContext = pContext;
    }

    private void validateParams() throws InvalidParameterSetException {
        for(Map.Entry<String, Object> item : mParam.valueSet()) {
            if(item.getValue().toString().isEmpty()) {
                throw new InvalidParameterSetException("Empty Parameter Value; Please check again...");
            }
        }
    }

    public Control getControl() throws InvalidParameterSetException {
        validateParams();
        if(mClName.equals(SwitchControl.class.getSimpleName())) {
            SwitchControl control = new SwitchControl(mParam.getAsString(Control.KEY_NAME), mParam.getAsInteger(Control.KEY_PIN_NUMBER), mContext);
            control.setShortDescription(mParam.getAsString(Control.KEY_SHORT_DESC));
            // update ControlManager
            mControlManager.occupyGpioPort(mParam.getAsInteger(Control.KEY_PIN_NUMBER));
            return control;
        } else if(mClName.equals(ButtonControl.class.getSimpleName())) {
            ButtonControl control = new ButtonControl(mParam.getAsString(Control.KEY_NAME), mParam.getAsInteger(Control.KEY_PIN_NUMBER), mContext);
            control.setShortDescription(mParam.getAsString(Control.KEY_SHORT_DESC));
            // update ControlManager
            mControlManager.occupyGpioPort(mParam.getAsInteger(Control.KEY_PIN_NUMBER));
            return control;
        } else if(mClName.equals(PwmControl.class.getSimpleName())) {
            PwmOutputType outputType = PwmOutputType.valueOf(mParam.getAsString(OutputControl.KEY_PWM_OUTPUT_TYP));
            PwmControl control = new PwmControl(mParam.getAsString(Control.KEY_NAME), mParam.getAsInteger(Control.KEY_PIN_NUMBER), mParam.getAsInteger(OutputControl.KEY_FREQUENCE), mParam.getAsInteger(OutputControl.KEY_DUTY_CYCLE), outputType, mContext);
            control.setShortDescription(mParam.getAsString(control.KEY_SHORT_DESC));
            // update ControlManager
            mControlManager.occupyGpioPort(mParam.getAsInteger(Control.KEY_PIN_NUMBER));
            return control;
        } else {
            return null;
        }
    }

    public static ArrayList<Control> getControlNullInstance(Context pContext) {
        ArrayList<Control> result = new ArrayList<>();
        result.add(new SwitchControl(pContext, null));
        result.add(new ButtonControl(pContext, null));
        result.add(new PwmControl(pContext, null));
        return result;
    }
}
