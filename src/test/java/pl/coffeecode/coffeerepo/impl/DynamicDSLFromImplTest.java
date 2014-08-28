package pl.coffeecode.coffeerepo.impl;

import static org.mockito.Mockito.mock;
import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;

import pl.coffeecode.coffeerepo.impl.DynamicDSLFromImpl;
import pl.coffeecode.coffeerepo.impl.DynamicDSLImpl;
import pl.coffeecode.coffeerepo.impl.QueryExecutor;

public class DynamicDSLFromImplTest {

    private DynamicDSLFromImpl dsl = null;

    private QueryExecutor delegate = mock(QueryExecutor.class);

    @Test
    public void should_construct_dsl_with_columns() {
        // when
        dsl = new DynamicDSLFromImpl(delegate, "col1", "col2");
        // then
        assertThat(dsl.attributes.columns).containsExactly("col1", "col2");
    }

    @Test
    public void tes() {
        // given
        dsl = new DynamicDSLFromImpl(delegate, "col1", "col2");
        // when
        DynamicDSLImpl dynamicDsl = (DynamicDSLImpl) dsl.from("view1");
        // then
        assertThat(dynamicDsl.attributes.columns).containsExactly("col1", "col2");
        assertThat(dynamicDsl.attributes.viewName).isEqualTo("view1");
    }

    @Test
    public void should_no_have_side_effects() {
        // given
        dsl = new DynamicDSLFromImpl(delegate, "col1", "col2");
        // when
        dsl.from("view1");
        // then
        assertThat(dsl.attributes.columns).containsExactly("col1", "col2");
        assertThat(dsl.attributes.viewName).isNull();
    }

}
