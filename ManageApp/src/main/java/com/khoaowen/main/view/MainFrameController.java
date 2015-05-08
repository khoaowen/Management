package com.khoaowen.main.view;

import java.io.ByteArrayInputStream;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import com.khoaowen.main.Main;
import com.khoaowen.main.mapper.PersonMapper;
import com.khoaowen.main.model.Person;
import com.khoaowen.main.model.Sex;

public class MainFrameController {
	
	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, String> lastNameColumn;
	@FXML
	private TableColumn<Person, String> firstNameColumn;
	@FXML
	private TextField lastName;
	@FXML
	private TextField firstName;
	@FXML
	private RadioButton male;
	@FXML
	private RadioButton female;
	@FXML
	private TextField religiousName;
	@FXML
	private TextField email;
	@FXML
	private DatePicker birthday;
	@FXML
	private ImageView imageView;
	
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
		
		// Clear person details.
	    showPersonDetails(null);
	    
	    personTable.getSelectionModel().selectedItemProperty().addListener(
	    		(observable, oldValue, newValue) -> showPersonDetails(newValue));
	}
	
	/**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.main = mainApp;
        // Add observable list data to the table
        personTable.setItems(FXCollections.observableArrayList(mainApp.getPersonMapper().getAll()));
    }
    
    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
        	firstName.setText(person.getFirstName());
            lastName.setText(person.getLastName());
            email.setText(person.getEmail());
            if (person.getSex() == Sex.MALE) {
            	male.setSelected(true);
            } else {
            	female.setSelected(true);
            }
            religiousName.setText(person.getReligiousName());
            birthday.setValue(person.getBirthday());
            if (person.getImage() != null) {
	            Image image = new Image(new ByteArrayInputStream(person.getImage()));
	            imageView.setImage(image);
            } else {
            	imageView.setImage(null);
            }

        } else {
            // Person is null, remove all the text.
            firstName.setText("");
            lastName.setText("");
            email.setText("");
            male.setSelected(false);
            female.setSelected(false);
            religiousName.setText("");
            birthday.setValue(null);
            imageView.setImage(null);
        }
    }
    
    @FXML
    void addNewElement() {
    	Person person = new Person();
		PersonMapper personMapper = main.getPersonMapper();
		// insert empty person in database
		personMapper.insert(person);
		
		// add this person to the table view
		personTable.getItems().add(person);
		
    }

}
