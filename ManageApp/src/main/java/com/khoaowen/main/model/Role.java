package com.khoaowen.main.model;

import com.khoaowen.utils.ResourceBundlesHelper;

public enum Role {

	/**
	 * Adopted child who lives in the temple
	 */
	LAY_BROTHER("role.laybrother.text"),
	/**
	 * Master who lives in the temple
	 */
	MASTER_BUDDHIST("role.master.text"),
	/**
	 * Ordinary people outside of the temple
	 */
	BUDDHIST("role.buddhist.text");
	
	
	private String bundleKey;
	
	private Role(String bundleKey) {
		this.bundleKey = bundleKey;
	}
	
	@Override
	public String toString() {
		return ResourceBundlesHelper.getMessageBundles(bundleKey);
	}
}
