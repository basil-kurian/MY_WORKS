package com.gogo.asp.utils.db;

/**
 * Created by BasilK[basil.kurian@aricent.com (CHE36539)] on 8/2/2016.
 */
public interface Column {

    String getName();

    SqlType getSqlType();

    Boolean isPrimaryKey();

    Boolean isIncludeInUpdate();

    String[] getConstraints();

}