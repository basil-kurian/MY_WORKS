package com.gogo.asp.utils.db.model;

/**
 * Created by BasilK[basil.kurian@aricent.com (che36539)] on 8/10/2016.
 */
public @interface Query {
    String name();

    String[] columns() default {};

    KeyOperation[] keys() default {};

}
