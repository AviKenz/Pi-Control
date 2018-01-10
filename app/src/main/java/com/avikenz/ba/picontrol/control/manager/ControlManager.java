package com.avikenz.ba.picontrol.control.manager;

import android.app.Application;

import com.avikenz.ba.picontrol.control.param.common.Mode;

/**
 * Created by AviKenz on 1/6/2018.
 * Class containings all data needed by a controller
 * Its also contain info about the GPIO
 * e.g how many are avaible and which can be set as GPIO or PWM
 * and things like this haha... i have kein Inspiration mehr :)
 */

public class ControlManager
        extends Application{


    private static String sServerUrl;
    private static Mode sMode;

    public void setServerUrl(String pUrl) {
        sServerUrl = pUrl;
    }

    public String getServerUrl() {
        if (sServerUrl == null) {
            // TODO [M] handle Exception
        }
        return sServerUrl;
    }

    public void setMode(Mode pMode) {
        sMode = pMode;
    }

    public Mode getMode() {
        if(sMode == null) {
            // TODO [M] handle Exception
        }
        return sMode;
    }
}
