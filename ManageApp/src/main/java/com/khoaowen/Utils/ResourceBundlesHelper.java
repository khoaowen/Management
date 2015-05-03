package com.khoaowen.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundlesHelper {

	private static String bundlePath = "bundles\\Bundle";
	private static ResourceBundle bundles = ResourceBundle.getBundle(bundlePath);
	
	public static String getMessageBundles(String key) {
		return bundles.getString(key);
	}
	
	public static String getMessageBundles(String key, Locale locale) {
		bundles = ResourceBundle.getBundle(bundlePath, locale); 
		return bundles.getString(key);
	}
	
	public static void setLocale(Locale locale) {
		bundles = ResourceBundle.getBundle(bundlePath, locale);
	}
	
	public static ResourceBundle getBundle() {
		return bundles;
	}
}
