package com.khoaowen.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class ResourceBundlesHelper {

	private static String bundlePath = "bundles/Bundle";
	private static ResourceBundle bundles = ResourceBundle.getBundle(bundlePath);
	
	public static String getMessageBundles(String key) {
		try {
			return bundles.getString(key);
		} catch (MissingResourceException e) {
			LogManager.getLogger(ResourceBundlesHelper.class).log(Level.WARN, e.getMessage());
			return key;
		}
	}
	
	public static String getMessageBundles(String key, Object... params) {
		return MessageFormat.format(bundles.getString(key), params);
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
