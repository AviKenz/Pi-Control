package com.avikenz.ba.picontrol.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.avikenz.ba.picontrol.R;
import com.avikenz.ba.picontrol.control.PwmControl;
import com.avikenz.ba.picontrol.control.SwitchControl;
import com.avikenz.ba.picontrol.control.manager.ControlManager;
import com.avikenz.ba.picontrol.control.manager.ControlManagerInterface;
import com.avikenz.ba.picontrol.control.param.common.Mode;


public class MainActivity
        extends AppCompatActivity
        implements ControlManagerInterface {

    ControlManager mControlManager = null;
    LinearLayout mControllerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControlManager();
        setupControler();
    }

    private void setupControler() {
        mControllerLayout = (LinearLayout) findViewById(R.id.controller_linear_layout);
        PwmControl test = new PwmControl("pwm_control_from_main", 5, 1000, 60, getApplicationContext());
        mControllerLayout.addView(test);
        SwitchControl test2 = new SwitchControl("switch_controler_from_main", 5, getApplicationContext());
        mControllerLayout.addView(test2);
    }

    /**
     * should be called before setContentView()
     */
    private void initControlManager() {
        mControlManager = getControlManager();
        // TODO [H] use dialog to get control manager setting from user
        mControlManager.setServerUrl("192.168.1.101");
        mControlManager.setMode(Mode.BCM);
    }

    @Override
    public ControlManager getControlManager() {
        return (ControlManager) getApplicationContext();
    }

    @Override
    public void updateControlManager(ControlManager pManager) {

    }
}
