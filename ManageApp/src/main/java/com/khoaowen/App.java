package com.khoaowen;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger logger = LogManager.getLogger("HelloWorld");
    public static void main( String[] args )
    {
    	logger.entry();
    	logger.info("Hello, World!");
    	try {
    		throw new Exception();
    	} catch (Exception e) {
    		logger.catching(e);
    	}
    	JOptionPane.showConfirmDialog(null, "Hello babe!");
    	logger.exit();
    }
}
