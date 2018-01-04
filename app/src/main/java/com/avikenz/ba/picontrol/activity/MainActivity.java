package com.avikenz.ba.picontrol.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.avikenz.ba.picontrol.R;
import com.avikenz.ba.picontrol.communication.PostHandler;
import com.avikenz.ba.picontrol.control.SwitchControl;
import com.avikenz.ba.picontrol.control.param.common.Mode;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
