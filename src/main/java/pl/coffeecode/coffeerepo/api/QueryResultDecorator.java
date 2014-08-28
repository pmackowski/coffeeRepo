package pl.coffeecode.coffeerepo.api;

import java.util.Map;

import pl.coffeecode.coffeerepo.impl.QueryResultImpl;
import pl.coffeecode.coffeerepo.impl.predicate.rowfunction.Nvl;
import pl.coffeecode.coffeerepo.impl.predicate.rowfunction.Pattern;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class QueryResultDecorator {

    private QueryResultDecorator() {

    }

	public static Builder decorate(QueryResult queryResult) {
		return new Builder(queryResult);
	}
	
	public static final class Builder {
		
		private QueryResult queryResult;
		private Table<Integer, String, Object> table;
		
		public Builder(QueryResult queryResult) {
			this.queryResult = queryResult;
			this.table = HashBasedTable.create(queryResult.items());
		}
		
		public QueryResult build() {
			return new QueryResultImpl(table, queryResult.sql(), queryResult.attributes(), queryResult.getTotalRecords());
		}
		
		public Builder column(String column, final String pattern, final String... columns) {
			return column(column, new Pattern(pattern, columns));
		}
		
		public Builder columnNvl(final String column, Object defaultValue) {
			return column(column, new Nvl(column, defaultValue));
		}
		
		public <T> Builder column(String column, RowFunction<T> rowFunction) {
			for (Integer key : table.rowKeySet()) {
				Map<String,Object> row = table.row(key);
				row.put(column, rowFunction.apply(row));
			}
			return this;
		}
		
		@SuppressWarnings("rawtypes")
		public Builder column(String column, CellFunction cellFunction) {
			Map<Integer, Object> map = table.column(column);
			for (Integer key : map.keySet()) {
				map.put(key, cellFunction.apply(map.get(key))); 
			}
			return this;
		}
		
		public Builder columns(CellFunction cellFunction, String column, String... columns) {
			column(column, cellFunction);
			for (String col : columns) {
				column(col, cellFunction);
			}
			return this;
		}
		
		public Builder removeColumns(String column, String... columns) {
			table.column(column).clear();
			for (String columnToRemove : columns) {
				table.column(columnToRemove).clear();
			}
			return this;
		}
		
	}

}
