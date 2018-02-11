package com.avikenz.ba.picontrol.control;

import android.content.ContentValues;
import com.avikenz.ba.picontrol.control.param.common.Direction;


/**
 * Created by AviKenz on 1/3/2018.
 * Common Interface for Views which cann send or recieve Signal from the Pi
 */

public interface OutputControl extends Control {

    /**
     * Keys of value used for the http request
     */

    String KEY_STATE = "state";
    String KEY_FREQUENCE = "freq";
    String KEY_DUTY_CYCLE = "duty_cycle";
    String KEY_SIGNAL_TYPE = "signal_type";
    String KEY_PWM_OUTPUT_TYP = "pwm_output_type";


    /**
     * direction of the Pin; Output
     */
    Direction direction = Direction.OUT;

    /**
     * return the name of the control
     * @return name of control
     */
    String getName();


    ContentValues getRequestParams();

}
