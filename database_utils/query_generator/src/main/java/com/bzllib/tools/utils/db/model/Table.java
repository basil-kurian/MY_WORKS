package com.bzllib.tools.utils.db.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by BasilK[basil.kurian@aricent.com (che36539)] on 8/10/2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {
    String value();

    boolean createOnlyIfNotExist() default true;

}
