package com.avikenz.ba.picontrol.communication;

import android.content.Context;

/**
 * Created by AviKenz on 1/4/2018.
 */

public class GetRequestHandler extends ControlRequest{

    public GetRequestHandler(Context pContext) {
        super(pContext);
    }

    @Override
    protected String doInBackground(String... params) {
        return null;
    }
}
