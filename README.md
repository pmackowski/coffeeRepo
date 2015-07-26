# coffeeRepo #

Simple queries for database views. Idea taken from
Implementing Domain-Driven Design by Vaughn Vernon - chapter about CQRS.

Supports H2 and Oracle.

```java

        DataSource dataSource = ...;

        DSL dsl = ViewRepository.dsl(dataSource);

        QueryResult result = dsl
                .select(C_NAME, C_LAST_NAME, C_AGE, C_DATE_OF_BIRTH, C_SALARY, C_BONUS)
                .from(VIEW_NAME)
                .where(
                        or(equal(C_LAST_NAME, "Lastname"), isNull(C_AGE)))
                .orderBy(
                        asc(C_LAST_NAME),
                        desc(C_AGE))
                .limit(5, 2)
                .getResult();

        // and result can be decorated

        QueryResult decorated = QueryResultDecorator.decorate(queryResult)
                .column(C_LAST_NAME, upperCase())
                .columnNvl(C_SALARY, 1000)
                .column("SALARY_AND_BONUS", sum(C_SALARY, C_BONUS))
                .removeColumns(C_NAME)
                .build();


```

QueryResult contains guava table

```java

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
```