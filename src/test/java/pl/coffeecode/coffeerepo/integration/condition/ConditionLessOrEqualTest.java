package pl.coffeecode.coffeerepo.integration.condition;

import static org.fest.assertions.api.Assertions.assertThat;
import static pl.coffeecode.coffeerepo.Constants.C_AGE;
import static pl.coffeecode.coffeerepo.Constants.C_NAME;
import static pl.coffeecode.coffeerepo.Constants.VIEW_NAME;
import static pl.coffeecode.coffeerepo.api.Predicate.lessOrEqual;

import java.util.Map;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Test;
import org.junit.runner.RunWith;

import pl.coffeecode.coffeerepo.DBUnitTest;
import pl.coffeecode.coffeerepo.SQLDialectDatasource;
import pl.coffeecode.coffeerepo.api.QueryResult;

@RunWith(JUnitParamsRunner.class)
public class ConditionLessOrEqualTest extends DBUnitTest {

    private static final String DATASET_FILE = "dbunit/condition_less_or_equal.xml";

    @Test
    @Parameters(method = "databases")
    public void should_return_less_or_equal_than_35(SQLDialectDatasource dialectDatasource) {
        prepare(dialectDatasource);
        QueryResult result = dsl

                .select(C_AGE)
                .from(VIEW_NAME)
                .where(lessOrEqual(C_AGE, 35))
                .getResult();

        assertThat(result.getTotalRecords()).isEqualTo(6);
        assertThat(rows(result.items())).are(new RowCondition() {

            @Override
            public boolean matches(Map<String, Object> value) {
                Number age = (Number) value.get(C_AGE);
                return age.intValue() <= 35;
            }

        });

    }

    @Test
    @Parameters(method = "databases")
    public void should_ignore_case_when_comparing_strings(SQLDialectDatasource dialectDatasource) {
        prepare(dialectDatasource);

        QueryResult result = dsl

                .select(C_NAME)
                .from(VIEW_NAME)
                .where(lessOrEqual(C_NAME, "kabana"))
                .getResult();

        assertThat(result.getTotalRecords()).isEqualTo(5);
        assertThat(rows(result.items())).are(new RowCondition() {

            @Override
            public boolean matches(Map<String, Object> value) {
                String name = value.get(C_NAME).toString().toUpperCase();
                return name.compareTo("KABANA") <= 0;
            }

        });

    }

    @Override
    protected String getDatasetFilePath() {
        return DATASET_FILE;
    }
}
