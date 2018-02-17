package com.avikenz.ba.picontrol.communication;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.avikenz.ba.picontrol.control.OutputControl;
import com.avikenz.ba.picontrol.control.management.ControlManager;

import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;


/**
 * Created by AviKenz on 1/4/2018.
 */

public class PostRequestHandler extends ControlRequest {

    public static final String TAG = PostRequestHandler.class.getSimpleName();

    public PostRequestHandler(OutputControl pControl, Context pContext) {
        super(pContext);
        init(pControl, pContext);
    }

    private void init(OutputControl pControl, Context pContext) {
        mControl = pControl;
        mContext = pContext;
    }

    @SuppressWarnings("TryWithIdenticalCatches")
    @Override
    protected String doInBackground(String... params) {
        try {
            // Generate URL
            URL postUrl = new URL(generateRequestUrl(mControl));
            mConnection = (HttpURLConnection) postUrl.openConnection();
            // Setup Connection
            // TODO [L] catch exception; see doc
            mConnection.setRequestMethod(METHOD_POST);
            mConnection.setReadTimeout(10000);
            mConnection.setConnectTimeout(5000);
            // Send Request
            mConnection.connect();
            // Get Response
            // TODO [H] implement notification to controller
            mResponseString = getResponseString(mConnection);
        } catch (MalformedURLException e) {
            // TODO [M] throw RuntimeException; check why
            //Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            // TODO [M] throw RuntimeException; check why
            //Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            Log.e(TAG, mResponseString);
            Log.e(TAG, "Response: " + mConnection.getResponseMessage());
            getRpiComMessage(mResponseString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            Toast.makeText(mContext, "Could not reach the RasPi; please Check the URL", Toast.LENGTH_LONG).show();
        }
    }
}
