package com.gogo.asp.utils.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BasilK[basil.kurian@aricent.com (CHE36539)] on 8/2/2016.
 */
public class DataBaseUtils {

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE ";
    public static final String IF_NOT_EXIST = "IF NOT EXISTS ";

    public static String generateCreateStatement(String tableName, Column[] columns, boolean ifNotExists) {
        Assert.notNull(tableName, "Table name cannot be Null");
        Assert.notNull(columns, "Create statement need columns");

        StringBuilder builder = new StringBuilder(CREATE_TABLE_QUERY);
        if (ifNotExists){
            builder.append(IF_NOT_EXIST);
        }
        builder.append(tableName).append(" (").append('\n');
        List<Column> primaryKeys = new ArrayList<>();
        for (Column column : columns){
            if (column.isPrimaryKey()) {
                primaryKeys.add(column);
            }
            builder.append("\t").append(column.getName()).append(" ").append(column.getSqlType());
            final String[] constraints = column.getConstraints();
            if (constraints != null && constraints.length != 0){
                for (int i = 0; i < constraints.length; i++) {
                    builder.append(" ").append(constraints[i]);
                }
            } else if (column.isPrimaryKey()) {
                builder.append(" ").append(" NOT NULL");
            }
            builder.append(",\n");
        }
        builder.append("PRIMARY KEY ( ");
        if (!primaryKeys.isEmpty()) {
            for (int i = 0; i < primaryKeys.size() - 1; i++) {
                builder.append(primaryKeys.get(i).getName()).append(", ");
            }
            builder.append(primaryKeys.get(primaryKeys.size() - 1).getName());
        }
        builder.append(" ))");

        return builder.toString();
    }



}
