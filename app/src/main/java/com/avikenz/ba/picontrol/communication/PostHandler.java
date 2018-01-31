package com.avikenz.ba.picontrol.communication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.avikenz.ba.picontrol.communication.util.ConnectionUtils;
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

    public static String COM_MSG_ID = "comMessage";
    public static String INTERPRETER_CLASS_NAME = "interpreter";
    public static String INTERPRETER_DEBUG_CLASS_NAME = INTERPRETER_CLASS_NAME + " debug";
    public static String INTERPRETER_INFO_CLASS_NAME = INTERPRETER_CLASS_NAME + " info";
    public static String INTERPRETER_WARN_CLASS_NAME = INTERPRETER_CLASS_NAME + " warn";
    public static String INTERPRETER_TODO_CLASS_NAME = INTERPRETER_CLASS_NAME + " todo";
    public static String INTERPRETER_ERROR_CLASS_NAME = INTERPRETER_CLASS_NAME + " error";
    public static String INTERPRETER_NONAME_CLASS_NAME = INTERPRETER_CLASS_NAME + " noname";

    OutputControl mControl;
    String mServerUrl;
    String mResponseString;
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
            mConnection.connect();
            // TODO [M] handle response like html to parse it well
            // TODO [H] implement notification to controller
            mResponseString = ConnectionUtils.getResponseString(mConnection);
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
            ConnectionUtils.getRpiComMessage(mResponseString);
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
