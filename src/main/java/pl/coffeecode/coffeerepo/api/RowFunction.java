package pl.coffeecode.coffeerepo.api;

import java.util.Map;

import com.google.common.base.Function;

public interface RowFunction<T> extends Function<Map<String, Object>, T> {

}
