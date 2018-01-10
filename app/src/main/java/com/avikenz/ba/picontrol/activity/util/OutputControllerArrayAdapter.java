package com.avikenz.ba.picontrol.activity.util;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avikenz.ba.picontrol.R;
import com.avikenz.ba.picontrol.control.ButtonControl;
import com.avikenz.ba.picontrol.control.Control;
import com.avikenz.ba.picontrol.control.OutputControl;

import java.util.List;

/**
 * Created by AviKenz on 1/9/2018.
 */

public class OutputControllerArrayAdapter extends ArrayAdapter<OutputControl> {

    private List<OutputControl> mControls;
    private int mResource;

    public OutputControllerArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<OutputControl> controls) {
        super(context, resource, controls);
        mControls = controls;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        OutputControl control = mControls.get(position);

        LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(getContext());
        RelativeLayout layout = (RelativeLayout) inflater.inflate(mResource, parent, false);

        ((TextView)layout.findViewById(R.id.port_type_textview)).setText(control.getPortType());
        ((TextView)layout.findViewById(R.id.view_description_textview)).setText(control.getViewDescription());
        //((ImageView)layout.findViewById(R.id.direction_imageview)).setImageResource(R.drawable.img_out);
        // Avoid illegal state exception view
        ViewParent v;
        if( (v = control.getView().getParent())!=null) {
            //((ViewGroup) control.getView().getParent()).removeView(control.getView());
            Log.e("ERROR", v.toString());
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        return layout;
    }

}
