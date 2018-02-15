package com.avikenz.ba.picontrol.control;

import android.content.ContentValues;
import android.view.View;

import com.avikenz.ba.picontrol.view.Editable;

/**
 * Interface which defined method needed by a Control. <br />
 * A Control is basically a Android view which perform an
 * Action when a {@code View} event occurs. <br />
 * The action to Perform
 * should be implemented in the appropriate {@code View} listener
 * @author AviKenz
 * @version 1.0
 * @since 1/9/2018.
 */

public interface Control extends Editable {

    String KEY_NAME = "name";
    String KEY_MODE = "mode";
    String KEY_DIRECTION = "direction";
    String KEY_PIN_NUMBER = "pin_number";
    String KEY_SHORT_DESC = "short_description";


    String LONG_VIEW_TYP = "long_view_typ";
    String SHORT_VIEW_TYP = "short_view_typ";

    /**
     * Get the {@link com.avikenz.ba.picontrol.control.management.PortType} name associated
     * to the control
     * @return The String representation of the port type
     */
    String getPortType();


    /**
     * get the pin number associated to the control
     * @return The number of the pin
     */
    int getPinNumber();


    /**
     * Get the Short description of the Control.
     * @return the description.
     */
    String getShortDescription();

    /**
     * Retrieve the description to show in the control view
     * @return the descrition to show
     */
    String getViewDescription();

    /**
     * Get the <code>View</code> of the Control
     * @return The View extended by the class implementing the Control Interface
     */
    View getView();

    /**
     * Get <code>VIEW_TYP</code> constant; they determine in {@link com.avikenz.ba.picontrol.view.ControlViewRow}
     * how many space to allocate to the Control View
     */
    String getViewType();
}
