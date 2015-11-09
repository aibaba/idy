package com.idy.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.Setter;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.idy.exception.resolver.StackTrace;
import com.idy.utils.IPUtil;

/**
 * 方法级别的权限拦截
 * @author gaopengbd
 *
 */
public class DrmPrivilegeInterceptor extends HandlerInterceptorAdapter {
	
	@Getter
	@Setter
	private List<String> allowUrls;
	
	protected org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DrmPrivilegeInterceptor.class);
	
	@Override
	public final boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {
			String ip = IPUtil.getRemortIP(request);
			String uri = request.getRequestURI();
			logger.info(String.format("ip=%s,uri=%s", ip, uri));
		} catch (Exception e) {
			logger.error(StackTrace.getExceptionTrace(e));
		}
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
}
