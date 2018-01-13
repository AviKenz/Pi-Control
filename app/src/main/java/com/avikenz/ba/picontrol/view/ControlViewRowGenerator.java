package com.avikenz.ba.picontrol.view;

import android.content.Context;
import android.widget.LinearLayout;

import java.util.Map;

/**
 * Created by AviKenz on 1/12/2018.
 */

public class ControlViewRowGenerator {

    private  static Generatable sControl;

    private static LinearLayout sView;
    private static LinearLayout.LayoutParams sLayoutParams;

    private static Context sContext;

    public static LinearLayout Generate(Generatable pControl, Context pContext) {
        sControl = pControl;
        sContext = pContext;
        sView = new LinearLayout(pContext);
        setLayoutParams();
        buildView();
        return sView;
    }

    private static void buildView() {
        sView.setOrientation(LinearLayout.VERTICAL);
        for(Map.Entry<String, Object> entry : sControl.getEditableFields().valueSet()) {
            sView.addView(new FormParamPairView(entry, sContext), sLayoutParams);
        }
    }

    private static void setLayoutParams() {
        sLayoutParams =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
}
