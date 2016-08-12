package com.gogo.asp.utils.db;

import com.gogo.asp.utils.db.model.QueryGenerator;

/**
 * Created by BasilK[basil.kurian@aricent.com (che36539)] on 8/10/2016.
 */
public class GenerateQuery {
    public static void main(String[] args) {
        QueryGenerator generator = new QueryGenerator(EntitlementRecordHolder.class);
        System.out.println(generator.getCreateQuery());
        System.out.println();
        System.out.println(generator.getInsertQuery());
        System.out.println();
        System.out.println(generator.getDelecteQuery());
        System.out.println();
        System.out.println(generator.getUpdateQuery());
    }
}
