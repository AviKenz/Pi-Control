package com.avikenz.ba.picontrol.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.avikenz.ba.picontrol.R;
import com.avikenz.ba.picontrol.control.manager.ControlManager;
import com.avikenz.ba.picontrol.control.manager.ControlManagerInterface;
import com.avikenz.ba.picontrol.control.param.common.Mode;


public class MainActivity
        extends AppCompatActivity
        implements ControlManagerInterface {

    ControlManager mControlManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControlManager();
    }



    /**
     * should be called before setContentView()
     */
    private void initControlManager() {
        mControlManager = getControlManager();
        // TODO [H] use dialog to get control manager setting from user
        mControlManager.setServerUrl("192.168.1.101");
        //mControlManager.setMode(Mode.BCM);
    }

    @Override
    public ControlManager getControlManager() {
        return (ControlManager) getApplicationContext();
    }

    @Override
    public void updateControlManager(ControlManager pManager) {

    }
}
