package com.khoaowen.main.view;

import com.khoaowen.main.Main;
import com.khoaowen.main.model.Person;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainFrameController {
	
	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, String> lastNameColumn;
	@FXML
	private TableColumn<Person, String> firstNameColumn;
	@FXML
	private Label lastName;
	@FXML
	private Label firstName;
	@FXML
	private Label sexe;
	@FXML
	private Label religiousName;
	
	private Main main;
	
	/**
	 * The constructor is called before the {@link #initialize} method
	 */
	public MainFrameController() {

	}
	
	/**
	 * Initializes the controller. This method is automatically called after the
	 * fxml file has been loaded
	 */
	@FXML
	private void initialize() {
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
	}
	
	/**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.main = mainApp;

        // Add observable list data to the table
        personTable.setItems(mainApp.getPersonData());
    }

}
