package com.avikenz.ba.picontrol.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.avikenz.ba.picontrol.R;
import com.avikenz.ba.picontrol.control.ButtonControl;
import com.avikenz.ba.picontrol.control.PwmControl;
import com.avikenz.ba.picontrol.control.SwitchControl;
import com.avikenz.ba.picontrol.control.manager.ControlManager;
import com.avikenz.ba.picontrol.control.manager.ControlManagerInterface;
import com.avikenz.ba.picontrol.control.param.common.Mode;
import com.avikenz.ba.picontrol.view.ControlViewRow;

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
        // TODO [N] try inflating control row view and add control to it
        mControllerLayout = (LinearLayout) findViewById(R.id.controller_linearlayout);
        SwitchControl swControl = new SwitchControl("switch_control", 5, getApplicationContext());
        ButtonControl btnControl = new ButtonControl("button_control", 6, getApplicationContext());
        PwmControl pwmControl = new PwmControl("pwm_control", 13, 100, 20, getApplicationContext());
        mControllerLayout.addView(new ControlViewRow(swControl, getApplicationContext()));
        mControllerLayout.addView(new ControlViewRow(btnControl, getApplicationContext()));
        mControllerLayout.addView(new ControlViewRow(pwmControl, getApplicationContext()));
    }

    private void initControlManager() {
        mControlManager = getControlManager();
        // TODO [H] use dialog to get control manager setting from user
        setServerUrl();
        //mControlManager.setServerUrl("192.168.1.101");
        mControlManager.setMode(Mode.BOARD);
    }

    @Override
    public ControlManager getControlManager() {
        return (ControlManager) getApplicationContext();
    }

    @Override
    public void updateControlManager(ControlManager pManager) {

    }

    public void setServerUrl() {
        final EditText input = new EditText(getBaseContext());
        input.setText("ServerUrl");
        new AlertDialog.Builder(this)
                .setView(input)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mControlManager.setServerUrl(input.getText().toString());
                    }
                }).show();
    }
}
