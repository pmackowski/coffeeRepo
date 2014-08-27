package pl.coffeecode.coffeerepo.impl;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import pl.coffeecode.coffeerepo.impl.driver.h2.H2Driver;

public class DynamicDSLSelectImplTest {
	
	private DynamicDSLSelectImpl dsl = null;
	
	@Before
	public void autowire() {
		DataSource dataSource = mock(DataSource.class);
		
		dsl = new DynamicDSLSelectImpl(dataSource, new H2Driver());
	}
	
	@Test
	public void should_create_dynamic_dsl_select_with_delegate_not_null() {
		assertThat(dsl.delegate).isNotNull();
	}
	
	@Test
	public void should_create_dynamic_dsl_from_and_set_attributes_columns_and_delegate() {
		DynamicDSLFromImpl dslFrom = (DynamicDSLFromImpl) dsl.select("col1","col2");
		
		assertThat(dslFrom.attributes.columns).containsExactly("col1","col2");
		assertThat(dslFrom.delegate).isNotNull();
		
	}
}
