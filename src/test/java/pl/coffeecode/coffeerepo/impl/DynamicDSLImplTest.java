package pl.coffeecode.coffeerepo.impl;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static pl.coffeecode.coffeerepo.api.Predicate.asc;
import static pl.coffeecode.coffeerepo.api.Predicate.desc;
import static pl.coffeecode.coffeerepo.api.Predicate.equal;

import org.junit.Before;
import org.junit.Test;

import pl.coffeecode.coffeerepo.api.Condition;
import pl.coffeecode.coffeerepo.api.Order;
import pl.coffeecode.coffeerepo.impl.AbstractDSL.QueryAttributesImpl;

public class DynamicDSLImplTest {

    private QueryExecutor delegate = mock(QueryExecutor.class);

    private QueryAttributesImpl attributes;

    @Before
    public void autowire() {
        attributes = new QueryAttributesImpl("col1", "col2");
        attributes.viewName = "view1";
    }

    @Test
    public void tesqd() {
        // given
        DynamicDSLImpl dsl = new DynamicDSLImpl(delegate, attributes);
        // when
        assertThat(dsl.attributes.getViewName()).isEqualTo("view1");
        assertThat(dsl.attributes.getColumns()).containsExactly("col1", "col2");
    }

    @Test
    public void tdcdwescads() {
        Condition nullCondition = null;
        DynamicDSLImpl dsl = (DynamicDSLImpl) newDynamicDSLImpl()
                .whereIgnoreNulls(nullCondition);

        assertThat(dsl.attributes.getCondition()).isNull();
    }

    @Test
    public void tdcdwdqdescads() {
        Condition nullCondition = null;
        DynamicDSLImpl dsl = (DynamicDSLImpl) newDynamicDSLImpl()
                .whereIgnoreNulls(nullCondition, equal("col1", 8));

        assertThat(dsl.attributes.getCondition()).isEqualTo(equal("col1", 8));
    }

    @Test
    public void tes() {
        DynamicDSLImpl dsl = (DynamicDSLImpl) newDynamicDSLImpl()
                .where(equal("col1", 8))
                .orderBy(asc("col2"), desc("col1"))
                .limit(10, 1);

        assertThat(dsl.attributes.getViewName()).isEqualTo("view1");
        assertThat(dsl.attributes.getColumns()).containsExactly("col1", "col2");
        assertThat(dsl.attributes.getCondition()).isEqualTo(equal("col1", 8));
        assertThat(dsl.attributes.getOrders()).containsExactly(asc("col2"), desc("col1"));
        assertThat(dsl.attributes.getNumberOfRows()).isEqualTo(10);
        assertThat(dsl.attributes.getPage()).isEqualTo(1);
    }


    @Test
    public void tdwes() {
        DynamicDSLImpl dsl = (DynamicDSLImpl) newDynamicDSLImpl()
                .orderBy(asc("col2"), desc("col1"))
                .appendOrderBy(asc("col3"));

        assertThat(dsl.attributes.getViewName()).isEqualTo("view1");
        assertThat(dsl.attributes.getColumns()).containsExactly("col1", "col2");

        assertThat(dsl.attributes.getOrders()).containsExactly(asc("col2"), desc("col1"), asc("col3"));
    }


    @Test
    public void tdcdwes() {
        DynamicDSLImpl dsl = (DynamicDSLImpl) newDynamicDSLImpl()
                .appendOrderBy(asc("col3"));

        assertThat(dsl.attributes.getOrders()).containsExactly(asc("col3"));
    }

    @Test
    public void tdcdwesds() {
        Order nullOrder = null;
        DynamicDSLImpl dsl = (DynamicDSLImpl) newDynamicDSLImpl()
                .appendOrderByIgnoreNulls(nullOrder);

        assertThat(dsl.attributes.getOrders()).isEmpty();
    }

    @Test
    public void tdcdwdsesds() {
        Order nullOrder = null;
        DynamicDSLImpl dsl = (DynamicDSLImpl) newDynamicDSLImpl()
                .appendOrderByIgnoreNulls(nullOrder, asc("col1"));

        assertThat(dsl.attributes.getOrders()).containsExactly(asc("col1"));
    }

    @Test(expected = NullPointerException.class)
    public void tdcdwdsesqwds() {
        Order nullOrder = null;
        newDynamicDSLImpl().appendOrderBy(nullOrder, asc("col1"));
    }

    @Test(expected = IllegalStateException.class)
    public void teefsfde() {
        newDynamicDSLImpl().where(equal("col1", 8)).where(equal("col2", 6));
    }

    @Test(expected = IllegalStateException.class)
    public void teefegsfde() {
        newDynamicDSLImpl().orderBy(asc("col1")).orderBy(desc("col2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void teefeewfgsfdewdw() {
        newDynamicDSLImpl().limit(0, 9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void teefeewfgsfde() {
        newDynamicDSLImpl().limit(1, 0);
    }

    private DynamicDSLImpl newDynamicDSLImpl() {
        return new DynamicDSLImpl(delegate, attributes);
    }
}
