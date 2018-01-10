package com.avikenz.ba.picontrol.control;

import android.view.View;

/**
 * Created by AviKenz on 1/9/2018.
 */

public interface Control {

    String KEY_NAME = "name";
    String KEY_MODE = "mode";
    String KEY_DIRECTION = "direction";
    String KEY_PIN_NUMBER = "pin_number";
    String KEY_SHORT_DESC = "short_description";

    /**
     * View typ used to determine which view will be used to render controller
     */
    String LONG_VIEW_TYP = "long_view_typ";
    String SHORT_VIEW_TYP = "short_view_typ";

    /**
     * return the port type linked with the controller
     * @return the port type
     */
    String getPortType();

    /**
     * Description of control
     * @return description
     */

    int getPinNumber();

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

    /**
     * return the view typ of the controler;
     * View Type determine how view are rendered (View disposition)
     */
    String getViewType();
}
