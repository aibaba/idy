package com.idy.common;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LdapUtils {
	
	private static DirContext ctx; 
	
	public static void main(String[] args) {
		getCtx();
	}

	@SuppressWarnings("unchecked")
	public static DirContext getCtx() {
		String account = "penggao15"; 
		String password = "Gaopeng.2";
		String root = "dc=adc,dc=cn"; 
		@SuppressWarnings("rawtypes")
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://localhost:389/" + root);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "cn=" + account);
		env.put(Context.SECURITY_CREDENTIALS, password);
		try {
			// 链接ldap
			ctx = new InitialDirContext(env);
			System.out.println("认证成功");
		} catch (javax.naming.AuthenticationException e) {
			System.out.println("认证失败");
		} catch (Exception e) {
			System.out.println("认证出错：");
			e.printStackTrace();
		}
		return ctx;
	}
}
