package com.idy.base;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.idy.constant.SystemConfig;
import com.idy.utils.CookieUtil;

public class BaseController {

public final static String BREADCRUMB = "breadcrumb";
	
	public static void initBasicInfo(Model model, List<String[]> breadcrumb) {
		model.addAttribute(BREADCRUMB, breadcrumb);
	}
	
	public static void initBasicInfo(HttpServletRequest request, Model model, String ... breadcrumb){
	    String userName = CookieUtil.getCookie(request, SystemConfig.COOKIE_USER_NAME);
		List<String[]> crumbList = new ArrayList<String[]>(2);
		for (int i = 0; i < breadcrumb.length; i += 2) {
			crumbList.add(new String[] { breadcrumb[i], breadcrumb[i + 1] });
		}
		model.addAttribute(BREADCRUMB, crumbList);
		model.addAttribute(SystemConfig.COOKIE_USER_NAME, userName);
	}
}
