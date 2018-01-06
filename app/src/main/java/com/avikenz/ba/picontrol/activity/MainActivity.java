package com.avikenz.ba.picontrol.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.avikenz.ba.picontrol.R;
import com.avikenz.ba.picontrol.control.manager.ControlManager;
import com.avikenz.ba.picontrol.control.manager.ControlManagerInterface;


public class MainActivity
        extends AppCompatActivity
        implements ControlManagerInterface {

    ControlManager mControlManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mControlManager = getControlManager();
        // TODO [H] use dialog to get server url from user
        mControlManager.setServerUrl("192.168.1.101");
    }

    @Override
    public ControlManager getControlManager() {
        return (ControlManager) getApplicationContext();
    }

    @Override
    public void updateControlManager(ControlManager pManager) {

    }
}
