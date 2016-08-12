package com.gogo.asp.utils.db.model;

import com.gogo.asp.utils.db.DBOperation;
import com.gogo.asp.utils.db.DataBaseUtils;

/**
 * Created by BasilK[basil.kurian@aricent.com (che36539)] on 8/10/2016.
 */
public class QueryGenerator {

    ClassWrapper clazz;
    private String createQuery = null;
    private String insertQuery = null;
    private String updateQuery = null;
    private String deleteQuery = null;

    private boolean isInit = false;

    public QueryGenerator(Class<?> clazz) {
        this.clazz = new ClassWrapper(clazz);
    }

    public String getCreateQuery(){
        if (createQuery == null){
            init();
            createQuery = DataBaseUtils.generateCreateStatement(clazz.getTableName(), clazz.getColumns(), clazz.isCreateIfNotExist());
        }
        return createQuery;
    }

    private void init() {
        if (!isInit){
            clazz.init();
            isInit = true;
        }
    }

    public String getInsertQuery(){
        if (insertQuery == null){
            init();
            insertQuery = DBOperation.INSERT.createPreparedQuery(clazz.getTableName(), clazz.getColumns());
        }
        return insertQuery;
    }

    public String getUpdateQuery(){
        if (updateQuery == null){
            init();
            updateQuery = DBOperation.UPDATE.createPreparedQuery(clazz.getTableName(), clazz.getUpdateColumns(), clazz.getPrimaryKeys());
        }
        return updateQuery;
    }

    public String getDelecteQuery(){
        if (deleteQuery == null) {
            init();
            deleteQuery = DBOperation.DELETE.createPreparedQuery(clazz.getTableName(), null, clazz.getPrimaryKeys());
        }
        return deleteQuery;
    }


}
