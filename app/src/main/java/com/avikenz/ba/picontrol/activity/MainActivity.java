package com.avikenz.ba.picontrol.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.avikenz.ba.picontrol.view.ControlView;
import com.avikenz.ba.picontrol.view.Editable;
import com.avikenz.ba.picontrol.view.FormParamPairView;
import com.avikenz.ba.picontrol.Exception.InvalidParameterSetException;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity
        extends AppCompatActivity
        implements ControlManagerInterface, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private final String TAG = this.getClass().getSimpleName();

    LinearLayout mControllerLayout;

    SwitchControl mSwControl;
    ButtonControl mButtonControl;
    PwmControl mIntPwmControl;
    PwmControl mBytePwmControl;

    ControlView mSwitchRow;
    ControlView mButtonRow;
    ControlView mSeekBarRow;
    ControlView mSeekBarRow2;

    NewEditableDialog mEditableDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupControler();
    }

    private void startControlViewGeneration() {
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
                if( !ControlManager.getInstace().isConfigured() ) {
                    Toast.makeText(MainActivity.this, "Control Manager is not configured !", Toast.LENGTH_LONG).show();
                } else {
                    generateControView(controls.get(which), MainActivity.this);
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void generateControView(Editable pEditable, Context pContext) {
        mEditableDialog = new NewEditableDialog(pEditable, pContext).create();
        mEditableDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(mEditableDialog.getEditable().getClazz().getName().equals(ControlManager.class.getName())) {
            ControlManager.getInstace().setServerUrl(mEditableDialog.getData().getAsString(ControlManager.KEY_SERVER_URL));
            ControlManager.getInstace().setMode(Mode.valueOf(mEditableDialog.getData().getAsString(ControlManager.KEY_NUMBERING_MODE)));
            ControlManager.getInstace().setConfigured(true);
            mEditableDialog.dismiss();
        } else {
            try {
                // this method should be bring outside
                mEditableDialog.appendToViewGroup(mControllerLayout);
                mEditableDialog.dismiss();
            } catch (InvalidParameterSetException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void configureControlManager() {
        mEditableDialog = new NewEditableDialog(ControlManager.getInstace(), MainActivity.this);
        mEditableDialog.show();
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
        mSwitchRow = new ControlView(mSwControl, getApplicationContext());
        mButtonRow = new ControlView(mButtonControl, getApplicationContext());
        mSeekBarRow = new ControlView(mIntPwmControl, getApplicationContext());
        mSeekBarRow2 = new ControlView(mBytePwmControl, getApplicationContext());

        // setup layout parameter
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        // appendToViewGroup views to layout
        mControllerLayout.addView(mSwitchRow, params);
        mControllerLayout.addView(mButtonRow, params);
        mControllerLayout.addView(mSeekBarRow, params);
        mControllerLayout.addView(mSeekBarRow2, params);
    }


    @Override
    public ControlManager getControlManager() {
        return (ControlManager) getApplicationContext();
    }

    @Override
    public void updateControlManager(ControlManager pManager) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.config_manager_menu_item:
                configureControlManager();
                break;
            case R.id.add_control_menu_item:
                startControlViewGeneration();
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

    public class NewEditableDialog {

        private String TAG = this.getClass().getSimpleName();

        private  Editable mEditable;

        private LinearLayout mView;
        private android.app.AlertDialog mDialog;
        private LinearLayout.LayoutParams mLayoutParams;

        private ArrayList<FormParamPairView> mRows;

        private Context mContext;
        // TODO [W] the context passed here should always be MainActivity.this
        public NewEditableDialog(Editable pEditable, Context pContext) {
            mEditable = pEditable;
            mContext = pContext;
            mRows = new ArrayList<>();
            mDialog = new AlertDialog.Builder(mContext)
                                      .setTitle("New " + pEditable.getClazz().getSimpleName())
                                      .setView(generateView())
                                      .setCancelable(true)
                                      .setPositiveButton("Create", null)
                                      .setNegativeButton("Cancel", null)
                                      .create();
            mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(final DialogInterface dialog) {
                    Button okBtn = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                    okBtn.setOnClickListener(MainActivity.this);
                }
            });
        }

        public ContentValues getData() {
            ContentValues result = new ContentValues();
            for(FormParamPairView item : mRows) {
                result.put(item.getKey(), item.getValue());
            }
            return result;
        }

        // dont call this method inside this class; return dialog instead
        public void show() {
            mDialog.show();
        }

        // should not be called inside the class
        // appendToViewGroup Controler row View to layout
        public void appendToViewGroup(ViewGroup pRootView) throws InvalidParameterSetException {
            ContentValues param = getData();
            Control control = new ControlFactory((Control) mEditable, param, MainActivity.this).getControl();
            ControlView controlView = new ControlView(control, MainActivity.this);
            pRootView.addView(controlView);
        }

        public LinearLayout generateView() {
            mView = new LinearLayout(mContext);
            setLayoutParams();
            generateViewContent();
            return mView;
        }

        public NewEditableDialog create() {
            return this;
        }


        private LinearLayout generateViewContent() {
            mView.setOrientation(LinearLayout.VERTICAL);
            FormParamPairView row;
            for(Map.Entry<String, Object> entry : mEditable.getEditableFields().valueSet()) {
                row = new FormParamPairView(entry, mContext);
                mView.addView(row, mLayoutParams);
                mRows.add(row);
            }
            return mView;
        }

        private void setLayoutParams() {
            mLayoutParams =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }

        public Dialog getDialog() {
            return mDialog;
        }

        public void dismiss() {
            mDialog.dismiss();
        }

        public Editable getEditable() {
            return mEditable;
        }


    }
}
