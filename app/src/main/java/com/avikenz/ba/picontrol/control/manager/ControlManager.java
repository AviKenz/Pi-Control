package com.avikenz.ba.picontrol.control.manager;

import android.app.Application;
import android.util.Log;

import com.avikenz.ba.picontrol.control.param.common.Mode;

/**
 * Created by AviKenz on 1/6/2018.
 * Class containings all data needed by a controller
 * Its also contain info about the GPIO
 * e.g how many are avaible and which can be set as GPIO or PWM
 * and things like this haha... i have kein Inspiration mehr :)
 */

public class ControlManager
        extends Application {

    // Define how many controls cann access a single port simultanously
    public static int sPortsUsableTime = 1;

    private static String sServerUrl;
    private static Mode sMode;

    public void setServerUrl(String pUrl) {
        sServerUrl = pUrl;
    }

    public static String getServerUrl() {
        if (sServerUrl == null) {
            // TODO [M] handle Exception
        }
        return sServerUrl;
    }

    public static void setMode(Mode pMode) {
        sMode = pMode;
    }

    public static Mode getMode() {
        if(sMode == null) {
            // TODO [M] handle Exception
            Log.e("ERR", "MODE is null");
        }
        return sMode;
    }
}
