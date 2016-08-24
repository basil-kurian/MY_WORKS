package com.bzllib.tools.utils.db.model;

import com.bzllib.tools.utils.db.Column;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by BasilK[basil.kurian@aricent.com (che36539)] on 8/10/2016.
 */
public class TableWrapper implements com.bzllib.tools.utils.db.Table {
    Class<?> clazz;

    List<Column> updateColumns = new ArrayList<>();
    // List to maintain order. Else Map was more suitable
    List<Column> columns = new ArrayList<>();
    List<Column> primaryKeys = new ArrayList<>();

    private String tableName = null;
    private boolean createIfNotExist = true;

    public TableWrapper(Class<?> clazz) {
        Assert.notNull(clazz, "Should not be null");
        this.clazz = clazz;
        init();
    }

    private void init(){
       if (clazz.isAnnotationPresent(Table.class)){
           processAnnotatedClass();
       } else {
           processUnAnnotatedClass();
       }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            Column column = processFieldAnnotation(field);
            if (column.isIncludeInUpdate()){
                updateColumns.add(column);
            }
            if (column.isPrimaryKey()) primaryKeys.add(column);
            columns.add(column);
        }

    }

    private void processAnnotatedClass(){
        Table table = clazz.getAnnotation(Table.class);
        if (table.value().equals("")){
            processUnAnnotatedClass();
            return;
        }
        tableName = table.value();
        createIfNotExist = table.createOnlyIfNotExist();
    }

    private void processUnAnnotatedClass() {
        tableName = clazz.getSimpleName();
        createIfNotExist = true;
    }

    private Column processFieldAnnotation(Field field) {
        return new ColumnParserImpl(field);
    }

    public String getTableName() {
        return this.tableName;
    }

    public boolean isCreateIfNotExist() {
        return this.createIfNotExist;
    }

    public Column[] getColumns() {
        return this.columns.toArray(new Column[columns.size()]);
    }

    public Column[] getUpdateColumns() {
        return  this.updateColumns.toArray(new Column[updateColumns.size()]);
    }

    public Column[] getPrimaryKeys() {
        return this.primaryKeys.toArray(new Column[primaryKeys.size()]);
    }

    // My own methods
    public void addPrimaryKeys(String[] keys){
        for (String key: keys){
            Column column = findColumnWithName(key);
            if (column != null && !column.isPrimaryKey()){
                ((ColumnParserImpl) column).setPrimaryKey(true);
                primaryKeys.add(column);
            }
        }
    }

    private Column findColumnWithName(String key) {
        for(Column column : columns) {
            if (column.getName().equals(key)){
                return column;
            }
        }
        return null;
    }

}
