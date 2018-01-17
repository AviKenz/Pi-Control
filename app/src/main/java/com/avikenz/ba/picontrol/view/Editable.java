package com.avikenz.ba.picontrol.view;

import android.content.ContentValues;

/**
 * Created by AviKenz on 1/17/2018.
 * Interface for all
 */

public interface Editable {

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
