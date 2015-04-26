package com.khoaowen.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class Resources {

	private static ResourceBundle bundles = ResourceBundle.getBundle("Bundle");
	
	public static String getMessageBundles(String key) {
		return bundles.getString(key);
	}
	
	public static String getMessageBundles(String key, Locale locale) {
		bundles = ResourceBundle.getBundle("Bundle", locale); 
		return bundles.getString(key);
	}
	
	public static void setLocale(Locale locale) {
		bundles = ResourceBundle.getBundle("Bundle", locale);
	}
	
	public static ResourceBundle getBundle() {
		return bundles;
	}
}
