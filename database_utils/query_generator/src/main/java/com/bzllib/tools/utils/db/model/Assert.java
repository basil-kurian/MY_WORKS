package com.bzllib.tools.utils.db.model;

/**
 * Created by BasilK[basil.kurian@aricent.com (che36539)] on 8/10/2016.
 */
public class Assert {
    public static void notNull(Object object , String errorMsg) {
        if (object == null) throw new IllegalArgumentException(errorMsg);
    }
}
