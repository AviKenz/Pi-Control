package com.avikenz.ba.picontrol.communication.util;

/**
 * Created by AviKenz on 1/5/2018.
 */


import android.util.Log;
import android.view.View;

import com.avikenz.ba.picontrol.communication.PostHandler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class ConnectionUtils {

    public static String getResponseString(HttpURLConnection conn)
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

    public static void getRpiComMessage(final String pHtmResponseString) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder builder = new StringBuilder();
                Document doc = Jsoup.parse(pHtmResponseString);
                Element comMsgEl = doc.getElementById(PostHandler.COM_MSG_ID);
                Elements errMsg = comMsgEl.getElementsByClass(PostHandler.INTERPRETER_ERROR_CLASS_NAME);
                Elements dbgMsg = comMsgEl.getElementsByClass(PostHandler.INTERPRETER_DEBUG_CLASS_NAME);
                Elements todoMsg = comMsgEl.getElementsByClass(PostHandler.INTERPRETER_TODO_CLASS_NAME);
                Elements nonameMsg = comMsgEl.getElementsByClass(PostHandler.INTERPRETER_NONAME_CLASS_NAME);
                Elements warnMsg = comMsgEl.getElementsByClass(PostHandler.INTERPRETER_WARN_CLASS_NAME);
                Elements infoMsg = comMsgEl.getElementsByClass(PostHandler.INTERPRETER_INFO_CLASS_NAME);
                Log.e("RESPONSE INT", getLog(comMsgEl.getElementsByTag("p")));
            }
        }).run();
    }

    private static String getLog(Elements pElements) {
        StringBuilder builder = new StringBuilder();
        for(Element item : pElements) {
            builder.append(item.text());
            builder.append("\n");
        }
        return builder.toString();
    }

}
