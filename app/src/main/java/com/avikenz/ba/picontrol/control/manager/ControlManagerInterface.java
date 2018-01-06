package com.avikenz.ba.picontrol.control.manager;

/**
 * Created by AviKenz on 1/6/2018.
 * Interface to invoque and update the control Manager
 */

public interface ControlManagerInterface {

    /**
     * used to get Control manager form Application Context:
     * @return the control manager
     */
    ControlManager getControlManager();

    /**
     * Update Control Manager field when some change ocurs in a class
     * @param pManager the control Manager
     */
    void updateControlManager(ControlManager pManager);
}
