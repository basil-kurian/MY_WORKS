package com.bzllib.tools.utils.db.model;

import com.bzllib.tools.utils.db.DataBaseUtils;
import com.bzllib.tools.utils.db.DBOperation;
import com.bzllib.tools.utils.db.Table;

/**
 * Created by BasilK[basil.kurian@aricent.com (che36539)] on 8/10/2016.
 */
public class QueryGenerator {

    Table clazz;

    public QueryGenerator(Class<?> clazz) {
        this.clazz = new TableWrapper(clazz);
    }

    public void setPrimaryKeys(String... keys){
        if (keys != null && keys.length > 0){
            ((TableWrapper) clazz).addPrimaryKeys(keys);
        }
    }

    public String getCreateQuery(){
        return DataBaseUtils.generateCreateStatement(clazz.getTableName(), clazz.getColumns(), clazz.isCreateIfNotExist());
    }

    public String getInsertQuery(){
        return DBOperation.INSERT.createPreparedQuery(clazz.getTableName(), clazz.getColumns());
    }

    public String getUpdateQuery(){
        return DBOperation.UPDATE.createPreparedQuery(clazz.getTableName(), clazz.getUpdateColumns(), clazz.getPrimaryKeys());
    }

    public String getDeleteQuery(){
       return DBOperation.DELETE.createPreparedQuery(clazz.getTableName(), null, clazz.getPrimaryKeys());
    }


}
