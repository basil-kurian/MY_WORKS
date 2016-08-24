package com.bzllib.tools.utils.db.model;

import com.bzllib.tools.utils.db.Column;

/**
 * Created by BasilK[basil.kurian@aricent.com (che36539)] on 8/24/2016.
 */
public interface ColumnParser extends Column {

    void setPrimaryKey(Boolean primaryKey);

    void setIncludeInUpdate(Boolean includeInUpdate);

}
