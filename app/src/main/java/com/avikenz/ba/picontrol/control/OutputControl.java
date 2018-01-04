package com.avikenz.ba.picontrol.control;

import android.content.ContentValues;
import android.util.Pair;

import org.apache.http.NameValuePair;

import java.util.List;
import java.util.Map;

/**
 * Created by AviKenz on 1/3/2018.
 * Common Interface for Views which cann send or recieve Signal from the Pi
 */

public interface OutputControl {

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
}
