package com.avikenz.ba.picontrol.view;

import android.content.ContentValues;

/**
 * Interface of all Class
 * @author AviKenz
 * @version 1.0
 * @since 1/17/2018.
 *
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
