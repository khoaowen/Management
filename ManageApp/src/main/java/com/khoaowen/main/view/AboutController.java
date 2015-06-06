package com.khoaowen.main.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import com.khoaowen.main.Main;

public class AboutController {
	
	private Main main;
	
	@FXML
	private TextArea textArea;
	
	/**
	 * The constructor is called before the {@link #initialize} method
	 */
	public AboutController() {
	}

	
	public void setMainApp(Main mainApp) {
        this.main = mainApp;
    }
	
	/**
	 * Initializes the controller. This method is automatically called after the
	 * fxml file has been loaded
	 */
	@FXML
	private void initialize() {
		initInformation();
	}


	private void initInformation() {
		StringBuilder builder = new StringBuilder();
		builder.append("Build information\n");
		builder.append("Version ");
		builder.append(getAppVersion());
		builder.append("\nJava version: ");
		builder.append(getJavaVersion());
		builder.append("\n\nChương trình Manage App được viết dành cho Tu viện An Lạc Hạnh");
		textArea.setText(builder.toString());
		
	}


	/**
	 * 
	 * @return the version of Java
	 */
	private String getJavaVersion() {
		return System.getProperty("java.version");
	}

	/**
	 * 
	 * @return the pom version of application
	 */
	private String getAppVersion() {
		String version = getClass().getPackage().getImplementationVersion();
		return version;
	}
	
}
