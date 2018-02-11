package com.avikenz.ba.picontrol.communication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.avikenz.ba.picontrol.control.OutputControl;
import com.avikenz.ba.picontrol.control.management.ControlManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;


/**
 * Created by AviKenz on 1/4/2018.
 */

public class PostRequestHandler extends ControlRequest {


    public PostRequestHandler(OutputControl pControl, Context pContext) {
        super(pContext);
        mControl = pControl;
        mContext = pContext;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL postUrl = new URL(generateRequestUrl(mControl));
            mConnection = (HttpURLConnection) postUrl.openConnection();
            // TODO [L] catch exception; see doc
            mConnection.setReadTimeout(10000);
            mConnection.setConnectTimeout(5000);
            mConnection.connect();
            // TODO [M] handle response like html to parse it well
            // TODO [H] implement notification to controller
            mResponseString = getResponseString(mConnection);
        } catch (MalformedURLException e) {
            // TODO [M] handle error - show dialog
        } catch (IOException e) {
            // TODO [M] handle error - show dialog
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            Log.e(TAG, mResponseString);
            Log.e(TAG, "Response: " + mConnection.getResponseMessage());
            // TODO HERE
            getRpiComMessage(mResponseString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            Toast.makeText(mContext, "Something Wrong with Connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if( !ControlManager.getInstace().isConfigured() ) {
            this.cancel(true);
            Toast.makeText(mContext, "Control Manager Not Configured", Toast.LENGTH_LONG).show();
        }
    }
}
