package com.avikenz.ba.picontrol.control.management;

/**
 * Created by AviKenz on 1/6/2018.
 * Interface to invoque and update the control Manager
 */

public interface ControlManagerInterface {

    /**
     * used to get Control manager form Application Context:
     * the manager should be get from application context !!!
     * @return the control manager
     */
    ControlManager getControlManager();

    /**
     * Update Control Manager field when some change ocurs in a class
     * @param pManager the control Manager
     */
    void updateControlManager(ControlManager pManager);
}