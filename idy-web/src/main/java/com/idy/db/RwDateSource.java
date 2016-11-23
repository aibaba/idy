package com.idy.db;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RwDateSource {

	/**
	 * 数据源策略，不明确指定数据源，只指定数据源类型。
	 * @return
	 */
	DatasourceStrategy strategy() default DatasourceStrategy.Default;
}
