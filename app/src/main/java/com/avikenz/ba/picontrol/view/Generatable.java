package com.avikenz.ba.picontrol.view;

import android.content.ContentValues;

/**
 * Created by AviKenz on 1/13/2018.
 * Interface for controller which can be generate by the user.
 */

public interface Generatable {

    /**
     * provide the runtime class for generation
     * @return the class to generate new instance
     */
    Class getClazz();

    /**
     * provide editable fields by instante generation.
     * fields consist on key and data typ.
     * @return the fields pair
     */
    ContentValues getEditableFields();
}
