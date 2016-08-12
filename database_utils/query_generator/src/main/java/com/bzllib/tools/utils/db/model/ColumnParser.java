package com.bzllib.tools.utils.db.model;

import com.bzllib.tools.utils.db.Column;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by BasilK[basil.kurian@aricent.com (che36539)] on 8/10/2016.
 */
public class ColumnParser implements Column {

    private String name;
    private SqlType sqlType;
    private Boolean isPrimaryKey = false;
    private Boolean isIncludeInUpdate = true;
    private String[] constraints;

    private static final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS
            = new HashMap<Class<?>, Class<?>>();
    static {
               PRIMITIVES_TO_WRAPPERS.put(boolean.class, Boolean.class);
               PRIMITIVES_TO_WRAPPERS.put(byte.class, Byte.class);
               PRIMITIVES_TO_WRAPPERS.put(char.class, Character.class);
               PRIMITIVES_TO_WRAPPERS.put(double.class, Double.class);
               PRIMITIVES_TO_WRAPPERS.put(float.class, Float.class);
               PRIMITIVES_TO_WRAPPERS.put(int.class, Integer.class);
               PRIMITIVES_TO_WRAPPERS.put(long.class, Long.class);
               PRIMITIVES_TO_WRAPPERS.put(short.class, Short.class);
               PRIMITIVES_TO_WRAPPERS.put(void.class, Void.class);
    }

    public ColumnParser(Field field) {
        if (field.isAnnotationPresent(Col.class)) {
            parseAnnotation(field);
        } else {
            name = field.getName();
            findSqlTypeFromField(field);
        }
    }

    private void parseAnnotation(Field field){

            Col col = field.getAnnotation(Col.class);
            if (col.value().isEmpty()){
                name = field.getName();
            } else {
                name = col.value();
            }


            sqlType = SqlType.valueOf(col.sqlType());
        if (sqlType == null ) throw new NullPointerException("Sql Type should be defined");
            if (sqlType == SqlType.UNKNOWN){
                findSqlTypeFromField(field);
            }
            isPrimaryKey = col.isPrimaryKey();
            isIncludeInUpdate = col.includeInUpdate();
            constraints = col.constraints();

    }

    private void findSqlTypeFromField (Field field) {
        String className = null;
        if (field.getType().isPrimitive()){
            Class clazz = field.getType();
            className = PRIMITIVES_TO_WRAPPERS.get(clazz).getSimpleName().toUpperCase();
        } else {
            className = field.getType().getSimpleName().toUpperCase();
        }
        SqlType sqlType;
        try {
            sqlType = SqlType.valueOf(className);
        } catch (IllegalArgumentException e) {
            sqlType = null;
        }
        if (sqlType == null){
            Class type = field.getType();

            if (Long.class.isAssignableFrom(type)) {
                className = "BIGINT";
            } else if (Double.class.isAssignableFrom(type)) {
                className = "REAL";
            } else if (Number.class.isAssignableFrom(type)){
                className = "NUMERIC";
            } else if (String.class.isAssignableFrom(type)){
                className = "VARCHAR";
            } else {
                className = "JAVA_OBJECT";
            }
            sqlType = SqlType.valueOf(className);
        }
        this.sqlType = sqlType;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.isPrimaryKey = primaryKey;
    }

    public void setIncludeInUpdate(Boolean includeInUpdate) {
        this.isIncludeInUpdate = includeInUpdate;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public SqlType getSqlType() {
        return this.sqlType;
    }

    @Override
    public Boolean isPrimaryKey() {
        return this.isPrimaryKey;
    }

    @Override
    public Boolean isIncludeInUpdate() {
        return this.isIncludeInUpdate;
    }

    @Override
    public String[] getConstraints() {
        return this.constraints;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{").append('\n');
        Field[]  fields = getClass().getDeclaredFields();
        for (Field field : fields)
            try {
                builder.append(" '").append(field.getName()).append("' : ").append(field.get(this)).append(", ").append("\n");
            } catch (IllegalAccessException e) {
                System.err.println(e);
            }
            builder.append(" }");
        return builder.toString();
    }
}
