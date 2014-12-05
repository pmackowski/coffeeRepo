package pl.coffeecode.coffeerepo.api;

import com.google.common.collect.Table;

public interface QueryResult {

    /**
     * @return SQL used for java.sql.PreparedStatement (with question marks)
     */
    String sql();

    QueryAttributes attributes();

    Table<Integer, String, Object> items();

    /**
     * @return if limit clause is used then total number, otherwise number of records returned by the query
     */
    int getTotalRecords();

    /**
     * @return if limit clause is used then total pages, otherwise 1
     */
    int getTotalPages();

}
