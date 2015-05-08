package com.khoaowen.main.view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Optional;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import org.apache.commons.lang3.tuple.Pair;

import com.khoaowen.main.Main;
import com.khoaowen.main.mapper.PersonMapper;
import com.khoaowen.main.model.Person;
import com.khoaowen.main.model.Sex;
import com.khoaowen.utils.ExceptionHandler;
import com.khoaowen.utils.ImageUtil;
import com.khoaowen.utils.JfxUtils;
import com.khoaowen.utils.ResourceBundlesHelper;

public class MainFrameController {
	
	private static final int IMAGE_SIZE = 24;
	
	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, String> numberColumn;
	@FXML
	private TableColumn<Person, String> deleteColumn;
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
	@FXML
	private Button addButton;
	@FXML
	private Button printButton;
	@FXML
	private ScrollPane formScrollpane;
	@FXML
	private Text changeImageHint;
	
	private Main main;
	
	private FileChooser fileChooser = new FileChooser();
	
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
		initTableLayout();
		// Clear person details.
	    showPersonDetails(null);
	    JfxUtils.configureFileChooser(fileChooser, "imageChooser.title.text", 
	    		Pair.of(ResourceBundlesHelper.getMessageBundles("allImages.extension.text"), "*.*"), 
	    		Pair.of("JPG", "*.jpg"), 
	    		Pair.of("PNG", "*.png"));
	    addButton.setGraphic(new ImageView(ImageUtil.getImageResources("/icon/add_person.png",IMAGE_SIZE,IMAGE_SIZE)));
	    printButton.setGraphic(new ImageView(ImageUtil.getImageResources("/icon/print.png", IMAGE_SIZE, IMAGE_SIZE)));
	    personTable.getSelectionModel().selectedItemProperty().addListener(
	    		(observable, oldValue, newValue) -> showPersonDetails(newValue));
	    imageView.setOnMouseEntered(event -> {
	    	imageView.setEffect(JfxUtils.borderGlow);
	    	changeImageHint.setVisible(true);
	    	main.getPrimaryStage().getScene().setCursor(Cursor.HAND);});
	    imageView.setOnMouseExited(event -> {
	    	imageView.setEffect(null);
	    	changeImageHint.setVisible(false);
	    	main.getPrimaryStage().getScene().setCursor(Cursor.DEFAULT);});
	    initBinding();
	}

	private void initBinding() {
//		firstName.textProperty().bindBidirectional(firstNameColumn.getCellFactory().);
//		lastName.textProperty().bindBidirectional(lastNameColumn.textProperty());
//		formScrollpane.visibleProperty().bind(Bindings.size(personTable.getItems()).greaterThan(0));
	}

	private void initTableLayout() {
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		numberColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<String>(personTable.getItems().indexOf(cellData.getValue())+1 + ""));
		numberColumn.setSortable(false);
		deleteColumn.setCellFactory(cellData -> {

			final TableCell<Person, String> cell = new TableCell<Person, String>() {
				@Override
				public void updateItem(String value, boolean empty) {
					final TableCell<Person, String> c = this;
					
					final VBox vbox = new VBox(5);
					if (c.getTableRow().getItem() != null) {
							
						Image image = ImageUtil.getImageResources("/icon/delete_person.png", 15, 15);
						ImageView graphic = new ImageView(image);
						Button button = new Button("", graphic);
						button.setTooltip(new Tooltip(ResourceBundlesHelper.getMessageBundles("delete.person.tooltip")));
						button.getStyleClass().add("deleteButton");
						button.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								TableRow<?> tableRow = c.getTableRow();
								Person item = (Person) tableRow.getTableView()
										.getItems().get(tableRow.getIndex());
								if (item == null) {
									return;
								}
								personTable.getSelectionModel().select(tableRow.getIndex());
								deletePerson();
							}
						});
						vbox.getChildren().add(button);
					}
					
					setGraphic(vbox);
				}
			};
			cell.setAlignment(Pos.TOP_RIGHT);
			return cell;
		});
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
        	displayForm(true);
            // Fill the labels with info from the person object.
        	firstName.setText(person.getFirstName());
            lastName.setText(person.getLastName());
            email.setText(person.getEmail());
            if (person.getSex() == Sex.FEMALE) {
            	female.setSelected(true);
            } else {
            	male.setSelected(true);
            }
            religiousName.setText(person.getReligiousName());
            birthday.setValue(person.getBirthday());
            if (person.getImage() != null) {
	            Image image = new Image(new ByteArrayInputStream(person.getImage()));
	            imageView.setImage(image);
            } else {
            	imageView.setImage(ImageUtil.getImageResources("/icon/person-placeholder.jpg"));
            }

        } else {
        	displayForm(false);
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
    
    private void displayForm(boolean display) {
    	formScrollpane.setVisible(display);
    	imageView.setVisible(display);
    }
    
    @FXML
    void addNewPerson() {
    	Person person = new Person();
		PersonMapper personMapper = main.getPersonMapper();
		// insert empty person in database
		personMapper.insert(person);
		
		// add this person to the table view
		personTable.getItems().add(person);
		personTable.getSelectionModel().select(person);
		personTable.scrollTo(person);
    }
    
    
    /**
     * Deletes selected person
     */
    void deletePerson() {
    	Optional<ButtonType> requestConfirmation = ExceptionHandler.requestConfirmation(ResourceBundlesHelper.getMessageBundles("delete.person.confirmation.question.text"));
    	if (requestConfirmation.get() == ButtonType.OK) { 
	    	PersonMapper personMapper = main.getPersonMapper();
	    	Person selectedItem = personTable.getSelectionModel().getSelectedItem();
	    	personTable.getItems().remove(selectedItem);
			if (selectedItem != null) {
				personMapper.deleteById(selectedItem.getId());
			}
    	}
    }
    
    @FXML
    void uploadImage() {
    	PersonMapper personMapper = main.getPersonMapper();
    	Person selectedItem = personTable.getSelectionModel().getSelectedItem();
    	if (selectedItem != null) {
	    	File file = fileChooser.showOpenDialog(main.getPrimaryStage());
	    	if (file != null) {
	    		selectedItem.setImage(ImageUtil.convertToByte(file.getAbsolutePath()));
	    	}
	    	personMapper.update(selectedItem);
	    	showPersonDetails(selectedItem);
    	}
    }
    
	

}
