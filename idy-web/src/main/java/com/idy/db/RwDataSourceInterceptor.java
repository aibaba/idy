package com.idy.db;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Aspect
@Component
public class RwDataSourceInterceptor {

	@Around(value = "@annotation(com.idy.db.RwDateSource)")
	public Object invoke(ProceedingJoinPoint pjp){
		try {
			Method method = ((MethodSignature)pjp.getSignature()).getMethod();
			Method targetMethod = pjp.getTarget().getClass().getMethod(method.getName(), method.getParameterTypes());
			
			//取注解@YRDatasource
			RwDateSource ds = targetMethod.getAnnotation(RwDateSource.class);
			if(ds!=null){
				/*//策略
				metadata.setStrategy(ds.strategy());
				LOGGER.debug("将数据源策略{}放入栈顶待用", ds.strategy());
				//数据源
				metadata.setDirectDatasource(ds.targetDatasource());
				if(StringUtils.hasText(ds.targetDatasource())){
					LOGGER.debug("将强制数据源{}放入栈顶待用",ds.targetDatasource());
				}*/
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			return pjp.proceed();
		} catch (Throwable e) {
			//logger.
			e.printStackTrace();
		}
		return pjp;
	}
}
