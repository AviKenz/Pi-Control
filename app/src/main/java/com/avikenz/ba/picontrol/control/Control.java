package com.avikenz.ba.picontrol.control;

import android.view.View;

/**
 * Created by AviKenz on 1/9/2018.
 */

public interface Control {

    /**
     * return the port type linked with the controller
     * @return the port type
     */
    String getPortType();

    /**
     * Description of control
     * @return description
     */
    String getShortDescription();

    /**
     * return the control description to show in the view
     * @return
     */
    String getViewDescription();

    /** Used to get the controller parameter for the http request
     *
     * @return the http request parameters
     */

    View getView();
}
