package com.test.exception.resolver;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.test.constant.Properties;

public class LogExceptionResolver extends SimpleMappingExceptionResolver {
    
    private Map<String, Logger> logMap = new HashMap<String,Logger>();
    
    private void logException(Exception exception) {
        String className = null;
        if (exception.getStackTrace() != null && exception.getStackTrace().length != 0) {
            className =  exception.getStackTrace()[0].getClassName();
        } else {
            className = this.getClass().getName();
        } 
        Logger logger = getLogger(className);
        logger.error("Http request error", exception);
    }
    
    private synchronized Logger getLogger(String className) {
        if (! logMap.containsKey(className)) {
            logMap.put(className, LoggerFactory.getLogger(className));
        }
        return logMap.get(className);
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {
        logException(ex);
        if(Properties.isPrintStackTrace()){
        	ex.printStackTrace();
        }
        if (ex instanceof SecurityException||ex instanceof IllegalAccessException){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else{
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        request.setAttribute("_debug", request.getParameter("_debug")!=null);
        return super.doResolveException(request, response, handler, ex);
    }
}

