package com.avikenz.ba.picontrol.control;

import android.content.ContentValues;
import android.util.Pair;
import android.view.View;
import android.widget.CompoundButton;

import com.avikenz.ba.picontrol.control.manager.ControlManagerInterface;
import com.avikenz.ba.picontrol.control.param.common.Direction;

import org.apache.http.NameValuePair;

import java.util.EventListener;
import java.util.List;
import java.util.Map;

/**
 * Created by AviKenz on 1/3/2018.
 * Common Interface for Views which cann send or recieve Signal from the Pi
 */

public interface OutputControl extends ControlManagerInterface {

    String KEY_NAME = "name";
    String KEY_MODE = "mode";
    String KEY_DIRECTION = "direction";
    String KEY_STATE = "state";
    String KEY_FREQUENCE = "freq";
    String KEY_DUTY_CYCLE = "duty_cycle";
    String KEY_SIGNAL_TYPE = "signal_type";
    String KEY_PIN_NUMBER = "pin_number";
    String KEY_SHORT_DESC = "short_description";

   Direction direction = Direction.OUT;

    /** get required param for the http request
     *
     * @return The param separated by space
     */
    public ContentValues getPostParams();

    /**
     * return the name of the control
     * @return name of control
     */
    public String getName();

    /**
     * Description of control
     * @return description
     */
    public String getShortDescription();

    /**
     * Retrieve server Url for the post request
     * @return
     */
    public String getServerUrl();

    /**
     * Define the action to execute when View change its state.
     * The Event type depent of the type of control and should be implemented by the class
     * Its Stricty recomended to call it in all Constructors.
     */
    // TODO [M] try to wrap this in a parent class BaseSwitchControl
    public void setChangeListener();

}
