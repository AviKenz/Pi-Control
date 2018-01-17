package com.avikenz.ba.picontrol.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avikenz.ba.picontrol.R;
import com.avikenz.ba.picontrol.control.ButtonControl;
import com.avikenz.ba.picontrol.control.Control;
import com.avikenz.ba.picontrol.control.PwmControl;
import com.avikenz.ba.picontrol.control.SwitchControl;
import com.avikenz.ba.picontrol.control.management.ControlManager;
import com.avikenz.ba.picontrol.control.management.ControlManagerInterface;
import com.avikenz.ba.picontrol.control.param.PwmOutputType;
import com.avikenz.ba.picontrol.control.param.common.Mode;
import com.avikenz.ba.picontrol.view.ControlFactory;
import com.avikenz.ba.picontrol.view.ControlViewRow;
import com.avikenz.ba.picontrol.view.FormParamPairView;
import com.avikenz.ba.picontrol.view.InvalidParameterSetException;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity
        extends AppCompatActivity
        implements ControlManagerInterface, NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = this.getClass().getSimpleName();

    ControlManager mControlManager = null;
    LinearLayout mControllerLayout;

    SwitchControl mSwControl;
    ButtonControl mButtonControl;
    PwmControl mIntPwmControl;
    PwmControl mBytePwmControl;

    ControlViewRow mSwitchRow;
    ControlViewRow mButtonRow;
    ControlViewRow mSeekBarRow;
    ControlViewRow mSeekBarRow2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initControlManager();
        setupControler();
    }

    private void startControlRowViewGeneration() {
        //
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Control");
        // setup view
        final ArrayList<Control> controls = ControlFactory.getControlNullInstance(this);
        int len = controls.size();
        String[] className = new String[len];
        for(int i = 0; i < len; i++) {
            className[i] = controls.get(i).getClazz().getSimpleName();
        }
        builder.setSingleChoiceItems(className, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new ControlViewRowGenerator(controls.get(which), MainActivity.this).show();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void setupControler() {
        // get reference to controller layout
        mControllerLayout = (LinearLayout) findViewById(R.id.controller_linearlayout);
        // create controllers
        mSwControl = new SwitchControl("switch_control", 5, getApplicationContext());
        mButtonControl = new ButtonControl("button_control", 6, getApplicationContext());
        mIntPwmControl = new PwmControl("seekbar_control_integer_integer_output", 13, 100, 0, PwmOutputType.INTEGER, getApplicationContext());
        mBytePwmControl = new PwmControl("seekbar_control_byte_output", 19, 100, 0, PwmOutputType.BYTE, getApplicationContext());
        // create control view and his details
        mSwitchRow = new ControlViewRow(mSwControl, getApplicationContext());
        mButtonRow = new ControlViewRow(mButtonControl, getApplicationContext());
        mSeekBarRow = new ControlViewRow(mIntPwmControl, getApplicationContext());
        mSeekBarRow2 = new ControlViewRow(mBytePwmControl, getApplicationContext());

        // setup layout parameter
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        // add views to layout
        mControllerLayout.addView(mSwitchRow, params);
        mControllerLayout.addView(mButtonRow, params);
        mControllerLayout.addView(mSeekBarRow, params);
        mControllerLayout.addView(mSeekBarRow2, params);
    }

    private void initControlManager() {
        mControlManager = ControlManager.getInstace();
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.config_manager_menu_item:
                break;
            case R.id.add_control_menu_item:
                startControlRowViewGeneration();
                break;
            case R.id.remove_control_menu_item:
                new AlertDialog.Builder(MainActivity.this)
                               .setTitle(R.string.remove_control)
                               .setMessage("Long Press On Control and Select Delete to delete Control.")
                               .setCancelable(true)
                               .create()
                               .show();
                break;
            case R.id.about_menu_item:
                // TODO [M] write about dialog
                break;

        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        NavigationView navView = (NavigationView) findViewById(R.id.app_menu_navigation_view);
        navView.setItemIconTintList(null);
        navView.setNavigationItemSelectedListener(this);
        return true;
    }


    /**
     * Created by AviKenz on 1/12/2018.
     */

    public class ControlViewRowGenerator {

        private String TAG = this.getClass().getSimpleName();

        private  Control mControl;

        private LinearLayout mView;
        private android.app.AlertDialog mDialog;
        private LinearLayout.LayoutParams mLayoutParams;

        private ArrayList<FormParamPairView> mRows;

        private Context mContext;
        // TODO [W] the context passed here should always be MainActivity.this
        public ControlViewRowGenerator(Control pControl, Context pContext) {
            mControl = pControl;
            mContext = pContext;
            mRows = new ArrayList<>();
            mDialog = new AlertDialog.Builder(mContext)
                                      .setTitle("Generate " + pControl.getClazz().getSimpleName())
                                      .setView(Generate(mContext))
                                      .setCancelable(true)
                                      .setPositiveButton("Create", null)
                                      .setNegativeButton("Cancel", null)
                                      .create();
            mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(final DialogInterface dialog) {
                    Button okBtn = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                add();
                                dialog.dismiss();
                            } catch (InvalidParameterSetException e) {
                                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }

        private ContentValues getData() {
            ContentValues result = new ContentValues();
            for(FormParamPairView item : mRows) {
                result.put(item.getKey(), item.getValue());
            }
            return result;
        }

        public void show() {
            mDialog.show();
        }

        // add Controler row View to layout
        private void add() throws InvalidParameterSetException {
            ContentValues param = getData();
            Control control = new ControlFactory(mControl, param, MainActivity.this).getControl();
            ControlViewRow controlViewRow = new ControlViewRow(control, MainActivity.this);
            mControllerLayout.addView(controlViewRow);
        }

        public LinearLayout Generate(Context pContext) {
            mView = new LinearLayout(pContext);
            setLayoutParams();
            buildView();
            return mView;
        }

        private void buildView() {
            mView.setOrientation(LinearLayout.VERTICAL);
            FormParamPairView row;
            for(Map.Entry<String, Object> entry : mControl.getEditableFields().valueSet()) {
                row = new FormParamPairView(entry, mContext);
                mView.addView(row, mLayoutParams);
                mRows.add(row);
            }
        }

        private void setLayoutParams() {
            mLayoutParams =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }


    }
}
