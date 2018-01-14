package com.avikenz.ba.picontrol.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.avikenz.ba.picontrol.R;
import com.avikenz.ba.picontrol.control.SwitchControl;
import com.avikenz.ba.picontrol.control.management.ControlManager;
import com.avikenz.ba.picontrol.control.management.ControlManagerInterface;
import com.avikenz.ba.picontrol.control.param.common.Mode;
import com.avikenz.ba.picontrol.view.ControlViewRow;
import com.avikenz.ba.picontrol.view.FormParamPairView;
import com.avikenz.ba.picontrol.view.Generatable;

import java.util.ArrayList;
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

        // TODO [I] use the Control second constructor to create oject for the generator; its always the same by all control.
        ControlViewRowGenerator gen = new ControlViewRowGenerator(new SwitchControl(getApplicationContext(), null), MainActivity.this);
        gen.show();
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

    /**
     * Created by AviKenz on 1/12/2018.
     */

    public class ControlViewRowGenerator {

        private String TAG = this.getClass().getSimpleName();

        private  Generatable mControl;

        private LinearLayout mView;
        private android.app.AlertDialog.Builder mBuilder;
        private LinearLayout.LayoutParams mLayoutParams;

        private ArrayList<FormParamPairView> mRows;

        private Context mContext;
        // TODO [W] the context passed here should always be MainActivity.this
        public ControlViewRowGenerator(Generatable pControl, Context pContext) {
            mControl = pControl;
            mContext = pContext;
            mRows = new ArrayList<>();
            mBuilder = new AlertDialog.Builder(mContext);
            mBuilder.setTitle("Generate " + pControl.getClazz().getSimpleName());
            mBuilder.setCancelable(true);
            mBuilder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(TAG, getData().toString());
                }
            });
            mBuilder.setNegativeButton("Cancel", null);
            mBuilder.setView(Generate(mControl, mContext));
        }

        private ContentValues getData() {
            ContentValues result = new ContentValues();
            for(FormParamPairView item : mRows) {
                result.put(item.getKey(), item.getValue());
            }
            return result;
        }

        public void show() {
            mBuilder.show();
        }

        public LinearLayout Generate(Generatable pControl, Context pContext) {
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
