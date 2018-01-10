package com.avikenz.ba.picontrol.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.avikenz.ba.picontrol.R;
import com.avikenz.ba.picontrol.activity.util.OutputControllerArrayAdapter;
import com.avikenz.ba.picontrol.control.Control;
import com.avikenz.ba.picontrol.control.OutputControl;
import com.avikenz.ba.picontrol.control.PwmControl;
import com.avikenz.ba.picontrol.control.SwitchControl;
import com.avikenz.ba.picontrol.control.manager.ControlManager;
import com.avikenz.ba.picontrol.control.manager.ControlManagerInterface;
import com.avikenz.ba.picontrol.control.param.common.Mode;

import java.util.ArrayList;
import java.util.List;


public class MainActivity
        extends AppCompatActivity
        implements ControlManagerInterface {

    ControlManager mControlManager = null;
    ListView mControllerListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControlManager();
        setupControler();
    }

    private void setupControler() {
        mControllerListView = (ListView) findViewById(R.id.controller_listview);
        //PwmControl test = new PwmControl("pwm_control_from_main", 5, 1000, 60, getBaseContext());
        SwitchControl test2 = new SwitchControl("switch_controler_from_main", 5, getBaseContext());
        List<OutputControl> controls = new ArrayList<>();
        //controls.add(test);
        controls.add(test2);
        OutputControllerArrayAdapter adapter = new OutputControllerArrayAdapter(this, R.layout.short_control_view, controls);
        mControllerListView.setAdapter(adapter);
    }

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
