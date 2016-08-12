package com.bzllib.tools.utils.db;

/**
 * Created by BasilK[basil.kurian@aricent.com (che36539)] on 8/12/2016.
 */
public interface Table {

    public String getTableName() ;

    public boolean isCreateIfNotExist() ;

    public Column[] getColumns() ;

    public Column[] getUpdateColumns() ;

    public Column[] getPrimaryKeys() ;

}
