package com.khoaowen.main.view;

import javafx.application.Platform;
import javafx.fxml.FXML;

import com.khoaowen.main.Main;

public class RootLayoutController {
	
	
	private Main main;

	/**
	 * The constructor is called before the {@link #initialize} method
	 */
	public RootLayoutController() {
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
		
	}
	
	@FXML
	private void newDatabase() {
		
	}
	
	@FXML
	private void openDatabase() {
		
	}
	
	@FXML
	private void closeApplication() {
		Platform.exit();
	}
}
