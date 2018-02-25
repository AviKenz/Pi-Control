package com.avikenz.ba.picontrol.communication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.avikenz.ba.picontrol.control.OutputControl;
import com.avikenz.ba.picontrol.control.management.ControlManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * Created by AviKenz on 2/11/2018.
 */

public abstract class ControlRequest
        extends AsyncTask<String, Void, String> {

    public static final String TAG = ControlRequest.class.getSimpleName();

    HttpURLConnection mConnection;
    // For the connection
    public static final String URL_SEPARATOR = "/";
    public static final String URL_PARAM_SEPARATOR = "?";
    public static final String FORM_PARAM_SEPARATO = "&";
    public static final String KEY_VALUE_SEPARATOR = "=";
    public static final String PROTOCOL = "http://";
    public static final String RPI_COM_IFACE_NAME = "RpiComIface.cgi";
    public static final String CGI_BIN_PATH = "/cgi-bin/";
    // For Logging
    public static final String LOG_LINE_TAG_NAME = "p";
    public static final String COM_MSG_ID = "comMessage";
    public static final String USER_APP_CLASS_NAME = "userapp";
    public static final String USER_DEBUG_CLASS_NAME = USER_APP_CLASS_NAME + " debug";
    public static final String USER_INFO_CLASS_NAME = USER_APP_CLASS_NAME + " info";
    public static final String USER_WARN_CLASS_NAME = USER_APP_CLASS_NAME + " warn";
    public static final String USER_TODO_CLASS_NAME = USER_APP_CLASS_NAME + " todo";
    public static final String USER_ERROR_CLASS_NAME = USER_APP_CLASS_NAME + " error";
    public static final String USER_NONAME_CLASS_NAME = USER_APP_CLASS_NAME + " noname";
    // HTTP Methoden
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";

    OutputControl mControl;
    ControlManager mControlManager;
    String mServerUrl;
    String mResponseString;
    Context mContext;

    public ControlRequest(Context pContext) {
        mContext = pContext;
        mControlManager = ControlManager.getInstace();
        mServerUrl = mControlManager.getServerUrl();

    }

    static String getResponseString(HttpURLConnection conn)
            throws IOException {
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        // retrieve the response from server
        InputStream is = null;
        try {
            is = conn.getInputStream();
            int ch;
            StringBuffer sb = new StringBuffer();
            while ((ch = is.read()) != -1) {
                sb.append((char) ch);
            }
            return sb.toString();
        } catch (IOException e) {
            throw e;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * get url for http post
     * @return the url
     */
    String generateRequestUrl(OutputControl pControl) {
        StringBuilder result = new StringBuilder();
        String queryString = generateRequestParams(pControl);
        Log.d(TAG, "QueryString: " + queryString);
        result.append(PROTOCOL);
        result.append(mServerUrl);
        result.append(CGI_BIN_PATH);
        result.append(RPI_COM_IFACE_NAME);
        result.append(URL_PARAM_SEPARATOR);
        result.append(queryString);
        Log.d(TAG, result.toString());
        return result.toString();
    }

    /**
     * generate form query string from control data
     * @param pControl concerned controller
     * @return the query string containig the named value from control
     */
    private String generateRequestParams(OutputControl pControl) {
        Log.v(TAG, "generateRequestParams() - params : " + pControl.getRequestParams().valueSet().toString());
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, Object> pair : pControl.getRequestParams().valueSet()) {
            if ( first ) {
                first = false;
                result.append(pair.getKey());
                result.append(KEY_VALUE_SEPARATOR);
                result.append(pair.getValue());
            } else {
                result.append(FORM_PARAM_SEPARATO);
                result.append(pair.getKey());
                result.append(KEY_VALUE_SEPARATOR);
                result.append(pair.getValue());
            }
        }
        return result.toString();
    }

    protected static void getRpiComMessage(final String pHtmResponseString) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder builder = new StringBuilder();
                Document doc = Jsoup.parse(pHtmResponseString);
                Element comMsgEl = doc.getElementById(COM_MSG_ID);
                Elements errMsg = comMsgEl.getElementsByClass(USER_ERROR_CLASS_NAME);
                Elements dbgMsg = comMsgEl.getElementsByClass(USER_DEBUG_CLASS_NAME);
                Elements todoMsg = comMsgEl.getElementsByClass(USER_TODO_CLASS_NAME);
                Elements nonameMsg = comMsgEl.getElementsByClass(USER_NONAME_CLASS_NAME);
                Elements warnMsg = comMsgEl.getElementsByClass(USER_WARN_CLASS_NAME);
                Elements infoMsg = comMsgEl.getElementsByClass(USER_INFO_CLASS_NAME);
                Log.e(TAG, getLogSession(comMsgEl.getElementsByTag(LOG_LINE_TAG_NAME)));
            }
        }).run();
    }

    protected static String getLogSession(Elements pElements) {
        StringBuilder builder = new StringBuilder();
        for(Element item : pElements) {
            builder.append(item.text());
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Cancel Request if ControlManager is not configured
        if( !mControlManager.isConfigured() ) {
            this.cancel(true);
            Toast.makeText(mContext, "Control Manager Not Configured", Toast.LENGTH_LONG).show();
        }
    }
}
