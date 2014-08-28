package pl.coffeecode.coffeerepo.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Map;
import java.util.Map.Entry;

import pl.coffeecode.coffeerepo.api.QueryAttributes;
import pl.coffeecode.coffeerepo.api.QueryResult;

import com.google.common.collect.Table;

public class QueryResultImpl implements QueryResult {

    private Table<Integer, String, Object> items;
    private String sql;
    private QueryAttributes queryAttributes;
    private int totalRecords;

    public QueryResultImpl(Table<Integer, String, Object> items, String sql, QueryAttributes queryAttributes, int totalRecords) {
        checkNotNull(items);
        checkNotNull(sql);
        checkNotNull(queryAttributes);
        checkArgument(totalRecords >= 0, "negative totalRecords: %s", totalRecords);
        this.items = items;
        this.sql = sql;
        this.queryAttributes = queryAttributes;
        this.totalRecords = totalRecords;
    }

    public Table<Integer, String, Object> items() {
        return items;
    }

    public String sql() {
        return sql;
    }

    public QueryAttributes attributes() {
        return queryAttributes;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("QueryResultImpl [\n");
        builder.append("\tsql = ").append(sql).append("\n");
        builder.append("\tqueryAttributes = ").append(queryAttributes).append("\n");
        builder.append("\ttotalRecords = ").append(totalRecords).append("\n");
        if (items.isEmpty()) {
            builder.append("\titems = no records found\n");
        } else {
            builder.append("\titems = \n");
            for (Entry<Integer, Map<String, Object>> entry : items.rowMap().entrySet()) {
                builder.append("\t\t").append(entry).append("\n");
            }
        }
        builder.append("]\n");
        return builder.toString();

    }

}
