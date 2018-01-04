package com.avikenz.ba.picontrol.communication;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.avikenz.ba.picontrol.control.OutputControl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;


/**
 * Created by AviKenz on 1/4/2018.
 */

public class PostHandler extends AsyncTask<String, Void, String> {

    public static final String TAG = PostHandler.class.getSimpleName();

    HttpURLConnection mConnection;

    public static String sUrlSeparator = "/";
    public static String sDataSeparator = "?";
    public static String sFormDataSeparator = "&";
    public static String sKeyValueSeparator = "=";
    public static String sProtocol = "http://";
    public static String sRpiComIfaceName  = "RpiComIface.cgi";
    public static String sCgiBinPath = "/cgi-bin/";

    OutputControl mControl;
    String mServerUrl;
    Context mContext;

    public PostHandler(OutputControl pControl, String pServerUrl, Context pContext) {

        mControl = pControl;
        mServerUrl = pServerUrl;
        mContext = pContext;
    }

    /**
     * get url for http post
     * @return the url
     */
    private String getPostUrl(OutputControl pControl) {
        StringBuilder result = new StringBuilder();
        String queryString = getQueryString(pControl);
        Log.d(TAG, "QueryString: " + queryString);
        result.append(sProtocol);
        result.append(mServerUrl);
        result.append(sCgiBinPath);
        result.append(sRpiComIfaceName);
        result.append(sDataSeparator);
        result.append(queryString);
        Log.d(TAG, result.toString());
        return result.toString();
    }

    /**
     * generate form query string from control data
     * @param pControl concerned controller
     * @return the query string containig the named value from control
     */
    private String getQueryString(OutputControl pControl) {
        Log.v(TAG, "getQueryString() - params : " + pControl.getPostParams().valueSet().toString());
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, Object> pair : pControl.getPostParams().valueSet()) {
            if ( first ) {
                first = false;
                result.append(pair.getKey());
                result.append(sKeyValueSeparator);
                result.append(pair.getValue());
            } else {
                result.append(sFormDataSeparator);
                result.append(pair.getKey());
                result.append(sKeyValueSeparator);
                result.append(pair.getValue());
            }
        }
        return result.toString();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL postUrl = new URL(getPostUrl(mControl));
            mConnection = (HttpURLConnection) postUrl.openConnection();
            // TODO [L] catch exception; see doc
            mConnection.setReadTimeout(10000);
            mConnection.setConnectTimeout(5000);
            mConnection.setRequestMethod("POST");
            mConnection.setDoInput(true);
            mConnection.setDoOutput(true);
            mConnection.connect();
        } catch (MalformedURLException e ) {
            // TODO [M] handle error - show dialog
        } catch (IOException e ) {
            // TODO [M] handle error - show dialog
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // TODO [H] check the post result
        String msg = "";
        try {
            msg = mConnection.getResponseMessage();
        } catch(Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "[" + msg + "]");
    }
}
