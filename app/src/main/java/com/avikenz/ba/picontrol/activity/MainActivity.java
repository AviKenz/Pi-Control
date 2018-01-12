package com.avikenz.ba.picontrol.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.avikenz.ba.picontrol.R;
import com.avikenz.ba.picontrol.control.SwitchControl;
import com.avikenz.ba.picontrol.control.management.ControlManager;
import com.avikenz.ba.picontrol.control.management.ControlManagerInterface;
import com.avikenz.ba.picontrol.control.management.Port;
import com.avikenz.ba.picontrol.control.param.common.Mode;
import com.avikenz.ba.picontrol.view.ControlViewRow;
import com.avikenz.ba.picontrol.view.FormParamPairView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity
        extends AppCompatActivity
        implements ControlManagerInterface {

    ControlManager mControlManager = null;
    LinearLayout mControllerLayout;

    SwitchControl mSwControl;
    SwitchControl mSwControl2;
    ControlViewRow mControlViewRow;
    ControlViewRow mControlViewRow2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControlManager();
        setupControler();

    }

    private void setupControler() {
        mControllerLayout = (LinearLayout) findViewById(R.id.controller_linearlayout);

        mSwControl = new SwitchControl("switch_control", 5, getApplicationContext());
        mSwControl2 = new SwitchControl("switch_control", 5, getApplicationContext());
        mControlViewRow = new ControlViewRow(mSwControl, getApplicationContext());
        mControlViewRow2 = new ControlViewRow(mSwControl2, getApplicationContext());



        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        mControllerLayout.addView(mControlViewRow, params);
        mControllerLayout.addView(mControlViewRow2, params);

        ContentValues pairs = new ContentValues();
        pairs.put("key is something good !", "value");
        for (Map.Entry<String, Object> el : pairs.valueSet()) {
            FormParamPairView pairView = new FormParamPairView(el, getApplicationContext());
            mControllerLayout.addView(pairView);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initControlManager() {
        mControlManager = getControlManager();
        // TODO [H] use dialog to get control manager setting from user
        setServerUrl();
        //mControlManager.setServerUrl("192.168.1.101");
        mControlManager.setMode(Mode.BCM);
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
