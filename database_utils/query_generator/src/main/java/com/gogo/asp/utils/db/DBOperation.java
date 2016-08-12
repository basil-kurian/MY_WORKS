package com.gogo.asp.utils.db;


/**
     * DB operations
     */
public enum DBOperation {
        /**
         * Insert Value into the table.
         * Use DBOperation.INSERT.createPreparedQuery() method will generate a prepared query
         */
    INSERT("INSERT OR REPLACE INTO ") {
        @Override
        protected void check(String tableName, Column[] columns, Column... criterion) {
            super.check(tableName, criterion, columns);
            if (columns == null || columns.length == 0) {
                throw new NullPointerException("Table Name cannot be null for INSERT operation");
            }
        }

        @Override
        public String createPreparedQuery(String tableName, Column[] columns, Column... criterion) {
            check(tableName, columns, criterion);
            StringBuilder builder = new StringBuilder(this.getStart()).append(tableName).append(" ");
            StringBuilder values = new StringBuilder(" VALUES ( ");
            if (columns != null && columns.length != 0) {
                builder.append("( ").append(columns[0].getName()); // To remove if check inside loop
                values.append(":").append(columns[0].getName());
                for (int i = 1; i < columns.length; i++) {
                    builder.append(", ").append(columns[i].getName());
                    values.append(", :").append(columns[i].getName());
                }
            }
            builder.append(" ) ");
            values.append(" )");
            builder.append(values);
            return builder.toString();
        }
    },

        /***
         * Delete Value from the table
         * User DBOperation.DELETE.createPreparedQuery() method will generate a prepared Query
         */
    DELETE("DELETE FROM ") {
        @Override
        public String createPreparedQuery(String tableName, Column[] columns, Column... criterion) {
            check(tableName, columns, criterion);
            StringBuilder builder = new StringBuilder(this.getStart());
            builder.append(tableName);
            if (criterion != null && criterion.length != 0) {
                builder.append(" WHERE ");
                for (int i = 0; i < criterion.length - 1; i++) {
                    builder.append(criterion[i].getName()).append(" = :").append(criterion[i].getName()).append(" AND ");
                }
                builder.append(criterion[criterion.length - 1].getName()).append(" = :").append(criterion[criterion.length - 1].getName());
            }
            return builder.toString();
        }

    },

        /***
         * Update Value in the table
         * User DBOperation.UPDATE.createPreparedQuery() method will generate a prepared Query
         * Assumption is that columns list will not have primary keys for update
         */
    UPDATE("UPDATE ") {
        @Override
        protected void check(String tableName, Column[] columns, Column... criterion) {
            super.check(tableName, columns, criterion);
            if (columns == null || columns.length == 0) {
                throw new NullPointerException("Table Name cannot be null for UPDATE operation");
            }
        }

        @Override
        public String createPreparedQuery(String tableName, Column[] columns, Column... criterion) {
            check(tableName, columns, criterion);
            StringBuilder builder = new StringBuilder(this.getStart());
            builder.append(tableName).append(" SET ");
            if (columns != null && columns.length != 0) {
                for (int i = 0; i < columns.length - 1; i++) {
                    builder.append(columns[i].getName()).append(" = :").append(columns[i].getName()).append(", ");
                }
                builder.append(columns[columns.length - 1].getName()).append(" = :").append(columns[columns.length - 1].getName());
            }
            if (criterion != null && criterion.length != 0) {
                builder.append(" WHERE ");
                for (int i = 0; i < criterion.length - 1; i++) {
                    builder.append(criterion[i].getName()).append(" = :").append(criterion[i].getName()).append(" AND ");
                }
                builder.append(criterion[criterion.length - 1].getName()).append(" = :").append(criterion[criterion.length - 1].getName());
            }
            return builder.toString();
        }
    },

        /***
         * Select Value from the table
         * User DBOperation.SELECT.createPreparedQuery() method will generate a prepared Query
         */
    SELECT("SELECT ") {
        @Override
        public String createPreparedQuery(String tableName, Column[] columns, Column... criterion) {
            check(tableName, criterion, columns);
            StringBuilder builder = new StringBuilder(this.getStart());
            if (columns == null || columns.length == 0) {
                builder.append(" * ");
            } else {
                for (int i = 0; i < columns.length - 1; i++) {
                    builder.append(columns[i].getName()).append(", ");
                }
                builder.append(columns[columns.length - 1].getName());
            }
            builder.append(" FROM ").append(tableName);

            if (criterion != null && criterion.length != 0) {
                builder.append(" WHERE ");
                for (int i = 0; i < criterion.length - 1; i++) {
                    builder.append(criterion[i].getName()).append(" = :").append(criterion[i].getName()).append(", ");
                }
                builder.append(criterion[criterion.length - 1].getName()).append(" = :").append(criterion[criterion.length - 1].getName());
            }
            return builder.toString();
        }
    };

    // All Variables
    private final String startQuery;

    DBOperation(String query) {
        this.startQuery = query;
    }

    protected String getStart() {
        return startQuery;
    }

    protected void check(String tableName, Column[] columns, Column... criterion) {
        if (tableName == null || tableName.isEmpty()) {
            throw new NullPointerException("Table Name cannot be null");
        }
    }

    /***
     * Creates a prepared Query for the table using the columns and
     * @param tableName - Name of table
     * @param criterion - Array of Primary keys
     * @param columns - Columns in Table
     * @return Query
     */
    public abstract String createPreparedQuery(String tableName, Column[] columns, Column... criterion);

}
