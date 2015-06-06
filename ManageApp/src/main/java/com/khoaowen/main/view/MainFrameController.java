package com.khoaowen.main.view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.apache.commons.lang3.tuple.Pair;

import com.khoaowen.main.Main;
import com.khoaowen.main.mapper.PersonMapper;
import com.khoaowen.main.model.Person;
import com.khoaowen.main.model.Role;
import com.khoaowen.main.model.Sex;
import com.khoaowen.utils.ExceptionHandler;
import com.khoaowen.utils.ImageUtil;
import com.khoaowen.utils.JfxUtils;
import com.khoaowen.utils.ResourceBundlesHelper;

public class MainFrameController {
	
	private static final int IMAGE_SIZE = 24;
	/**
	 * row number of sala in the gridpane form
	 */
	private static final int SILA_ROW_OF_GRIDPANE = 12;
	
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
	private TableColumn<Person, Role> roleColumn;
	@FXML
	private TextField lastName;
	@FXML
	private TextField firstName;
	@FXML
	private RadioButton male;
	@FXML
	private RadioButton female;
	@FXML
	private TextField sila;
	@FXML
	private Label silaLabel;
	@FXML
	private TextField religiousName;
	@FXML
	private Label religiousNameLabel;
	@FXML
	private TextField email;
	@FXML
	private TextField ethnicGroup;
	@FXML
	private TextField nationality;
	@FXML
	private TextField hometown;
	@FXML
	private TextArea placeOfResidence;
	@FXML
	private TextArea placeOfTempResidence;
	@FXML
	private DatePicker birthday;
	@FXML
	private TextField placeOfBirth;
	@FXML
	private DatePicker religiousDate;
	@FXML
	private DatePicker adoptedDate;
	@FXML
	private Label adoptedDateLabel;
	@FXML
	private TextField idNumber;
	@FXML
	private DatePicker idNumberIssueDate;
	@FXML
	private TextField idNumberIssuePlace;
	@FXML
	private TextField phoneNumber;
	@FXML
	private TextField fatherFullName;
	@FXML
	private Label fatherFullNameLabel;
	@FXML
	private TextField motherFullName;
	@FXML
	private Label motherFullNameLabel;
	@FXML
	private TextField studyLevel;
	@FXML
	private Label studyLevelLabel;
	@FXML
	private TextField languageLevel;
	@FXML
	private Label languageLevelLabel;
	@FXML
	private TextArea note;
	@FXML
	private ImageView imageView;
	@FXML
	private MenuButton addButton;
	@FXML
	private Button printButton;
	@FXML
	private ScrollPane formScrollpane;
	@FXML
	private Text changeImageHint;
	@FXML
	private StackPane stackPane;
	@FXML
	private GridPane gridpane;
	
	private Main main;
	
	private Person currentSelected;
	
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
	    
        birthday.setPromptText(getPatternFromDatePicker(birthday));
        religiousDate.setPromptText(getPatternFromDatePicker(religiousDate));
        adoptedDate.setPromptText(getPatternFromDatePicker(adoptedDate));
        idNumberIssueDate.setPromptText(getPatternFromDatePicker(idNumberIssueDate));
        
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
	    initListeners();
	}

	/**
	 * Initializes listeners for input control.
	 * Any changes by user must be persisted in database.
	 */
	private void initListeners() {
		lastName.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setLastName(newValue);
						updatePerson(selectedItem);
					}
				});
		firstName.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setFirstName(newValue);
						updatePerson(selectedItem);
					}
				});
		email.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setEmail(newValue);
						updatePerson(selectedItem);
					}
				});
		male.selectedProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setSex(newValue?Sex.MALE:Sex.FEMALE);
						updatePerson(selectedItem);
					}
				});
		female.selectedProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setSex(newValue?Sex.FEMALE:Sex.MALE);
						updatePerson(selectedItem);
					}
				});
		birthday.valueProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setBirthday(newValue);
						updatePerson(selectedItem);
					}
				});
		placeOfBirth.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setPlaceOfBirth(newValue);
						updatePerson(selectedItem);
					}
				});
		ethnicGroup.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setEthnicGroup(newValue);
						updatePerson(selectedItem);
					}
				});
		nationality.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setNationality(newValue);
						updatePerson(selectedItem);
					}
				});
		hometown.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setHometown(newValue);
						updatePerson(selectedItem);
					}
				});
		placeOfResidence.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setPlaceOfResidence(newValue);
						updatePerson(selectedItem);
					}
				});
		placeOfTempResidence.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setPlaceOfTempResidence(newValue);
						updatePerson(selectedItem);
					}
				});
		religiousName.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setReligiousName(newValue);
						updatePerson(selectedItem);
					}
				});
		sila.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setSila(newValue);
						updatePerson(selectedItem);
					}
				});
		religiousDate.valueProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setReligiousDate(newValue);
						updatePerson(selectedItem);
					}
				});
		adoptedDate.valueProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setAdoptedDate(newValue);
						updatePerson(selectedItem);
					}
				});
		idNumber.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setIdNumber(newValue);
						updatePerson(selectedItem);
					}
				});
		idNumberIssueDate.valueProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setIdNumberIssueDate(newValue);
						updatePerson(selectedItem);
					}
				});
		idNumberIssuePlace.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setIdNumberIssuePlace(newValue);
						updatePerson(selectedItem);
					}
				});
		phoneNumber.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setPhoneNumber(newValue);
						updatePerson(selectedItem);
					}
				});
		fatherFullName.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setFatherFullName(newValue);
						updatePerson(selectedItem);
					}
				});
		motherFullName.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setMotherFullName(newValue);
						updatePerson(selectedItem);
					}
				});
		studyLevel.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setStudyLevel(newValue);
						updatePerson(selectedItem);
					}
				});
		languageLevel.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setLanguageLevel(newValue);
						updatePerson(selectedItem);
					}
				});
		note.textProperty().addListener(
				(observable, oldValue, newValue) -> {
					Person selectedItem = personTable.getSelectionModel().getSelectedItem();
					if (shouldUpdatePersonInDatabase(oldValue, newValue, selectedItem)) {
						selectedItem.setNote(newValue);
						updatePerson(selectedItem);
					}
				});
	}
	
	/**
	 * Check if user has modified the data via the form in order to update the database for the current person.
	 * However, No update query should be done if user changes person via the list.
	 * @param oldValue
	 * @param newValue
	 * @param selectedItem
	 * @return 
	 */
	private <T> boolean shouldUpdatePersonInDatabase(T oldValue, T newValue, Person selectedItem) {
		if (oldValue == null && newValue == null) {
			return false;
		}
		if ((oldValue == null && newValue != null) || !oldValue.equals(newValue)) {
			// check if user did not change item by clicking on the list
			if (currentSelected != null && selectedItem != null 
					&& selectedItem.getId() == currentSelected.getId()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get the pattern format of date from {@code datePicker}
	 * @param datePicker
	 * @return
	 */
	private String getPatternFromDatePicker(DatePicker datePicker) {
		Locale locale = Locale.getDefault(Locale.Category.FORMAT);
        Chronology chrono = birthday.getChronology();
        String pattern =
            DateTimeFormatterBuilder.getLocalizedDateTimePattern(FormatStyle.SHORT,
                                                                 null, chrono, locale);
        if (pattern.contains("yy") && !pattern.contains("yyy")) {
            // Modify pattern to show four-digit year, including leading zeros.
            return pattern.replace("yy", "yyyy");
        }
        return pattern;
	}

	private void initBinding() {
//		firstName.textProperty().bindBidirectional(firstNameColumn.getCellFactory().);
//		lastName.textProperty().bindBidirectional(lastNameColumn.textProperty());
//		formScrollpane.visibleProperty().bind(Bindings.size(personTable.getItems()).greaterThan(0));
	}

	private void initTableLayout() {
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		roleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
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
								deletePerson(item);
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
        PersonMapper personMapper = mainApp.getPersonMapper();
        if (personMapper != null) {
        	personTable.setItems(FXCollections.observableArrayList(personMapper.getAll()));
        }
    }
    
    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void showPersonDetails(Person personId) {
    	Person person = null;
    	if (personId != null) {
    		PersonMapper personMapper = main.getPersonMapper();
    		person = personMapper.getById(personId.getId());
    	}
        if (person != null) {
        	displayForm(true);
        	configureForm(person.getRole());
        	
            // Fill the labels with info from the person object.
        	if (person.getImage() != null) {
	            Image image = new Image(new ByteArrayInputStream(person.getImage()));
	            imageView.setImage(image);
            } else {
            	imageView.setImage(ImageUtil.getImageResources("/icon/person-placeholder.jpg"));
            }
        	firstName.setText(person.getFirstName());
            lastName.setText(person.getLastName());
            email.setText(person.getEmail());
            
            if (person.getSex() == null) {
            	male.setSelected(false);
				female.setSelected(false);
            } else if (person.getSex() == Sex.FEMALE) {
            	male.setSelected(false);
				female.setSelected(true);
            } else {
            	male.setSelected(true);
				female.setSelected(false);
            }
            sila.setText(person.getSila());
            religiousName.setText(person.getReligiousName());
            birthday.setValue(person.getBirthday());
            placeOfBirth.setText(person.getPlaceOfBirth());
            religiousDate.setValue(person.getReligiousDate());
            ethnicGroup.setText(person.getEthnicGroup());
            nationality.setText(person.getNationality());
            hometown.setText(person.getHometown());
            placeOfResidence.setText(person.getPlaceOfResidence());
            placeOfTempResidence.setText(person.getPlaceOfTempResidence());
            adoptedDate.setValue(person.getAdoptedDate());
            idNumber.setText(person.getIdNumber());
            idNumberIssueDate.setValue(person.getIdNumberIssueDate());
            idNumberIssuePlace.setText(person.getIdNumberIssuePlace());
            phoneNumber.setText(person.getPhoneNumber());
            fatherFullName.setText(person.getFatherFullName());
            motherFullName.setText(person.getMotherFullName());
            studyLevel.setText(person.getStudyLevel());
            languageLevel.setText(person.getLanguageLevel());
            note.setText(person.getNote());

        } else {
        	displayForm(false);
            // Person is null, remove all the text.
        	imageView.setImage(null);
            firstName.setText("");
            lastName.setText("");
            email.setText("");
            male.setSelected(false);
            female.setSelected(false);
            sila.setText("");
            religiousName.setText("");
            birthday.setValue(null);
            placeOfBirth.setText("");
            religiousDate.setValue(null);
            ethnicGroup.setText("");
            nationality.setText("");
            hometown.setText("");
            placeOfTempResidence.setText("");
            placeOfResidence.setText("");
            adoptedDate.setValue(null);
            idNumber.setText("");
            idNumberIssueDate.setValue(null);
            idNumberIssuePlace.setText("");
            phoneNumber.setText("");
            fatherFullName.setText("");
            motherFullName.setText("");
            studyLevel.setText("");
            languageLevel.setText("");
            note.setText("");
        }
        
        // update the current selected person once the datas are updated
        currentSelected = person;
    }
    
    private void configureForm(Role role) {
		switch (role) {
		case BUDDHIST:
			sila.setDisable(true);
			silaLabel.setDisable(true);
			religiousName.setDisable(false);
			religiousNameLabel.setDisable(false);
			adoptedDate.setDisable(true);
			adoptedDateLabel.setDisable(true);
			studyLevel.setDisable(true);
			studyLevelLabel.setDisable(true);
			languageLevel.setDisable(true);
			languageLevelLabel.setDisable(true);
			motherFullName.setDisable(true);
			motherFullNameLabel.setDisable(true);
			fatherFullName.setDisable(true);
			fatherFullNameLabel.setDisable(true);
			break;
		case LAY_BROTHER:
			sila.setDisable(true);
			silaLabel.setDisable(true);
			religiousName.setDisable(false);
			religiousNameLabel.setDisable(false);
			adoptedDate.setDisable(false);
			adoptedDateLabel.setDisable(false);
			studyLevel.setDisable(false);
			studyLevelLabel.setDisable(false);
			languageLevel.setDisable(false);
			languageLevelLabel.setDisable(false);
			motherFullName.setDisable(false);
			motherFullNameLabel.setDisable(false);
			fatherFullName.setDisable(false);
			fatherFullNameLabel.setDisable(false);
			break;
		case MASTER_BUDDHIST:
			sila.setDisable(false);
			silaLabel.setDisable(false);
			religiousName.setDisable(true);
			religiousNameLabel.setDisable(true);
			adoptedDate.setDisable(false);
			adoptedDateLabel.setDisable(false);
			studyLevel.setDisable(false);
			studyLevelLabel.setDisable(false);
			languageLevel.setDisable(false);
			languageLevelLabel.setDisable(false);
			motherFullName.setDisable(true);
			motherFullNameLabel.setDisable(true);
			fatherFullName.setDisable(true);
			fatherFullNameLabel.setDisable(true);
			break;
		}
		
	}

	private void displayForm(boolean display) {
    	formScrollpane.setVisible(display);
    	imageView.setVisible(display);
    	printButton.setDisable(!display);
    }
    
    @FXML
    void addNewBuddhist() {
    	Person person = new Person();
    	person.setRole(Role.BUDDHIST);
    	persistPerson(person);
    }
    
    @FXML
    void addNewMaster() {
    	Person person = new Person();
    	person.setRole(Role.MASTER_BUDDHIST);
    	persistPerson(person);
    }
    
    @FXML
    void addNewLayBrother() {
    	Person person = new Person();
    	person.setRole(Role.LAY_BROTHER);
    	persistPerson(person);
    }
    
    private void persistPerson(Person person) {
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
    void deletePerson(Person person) {
    	Optional<ButtonType> requestConfirmation = ExceptionHandler.requestConfirmation(ResourceBundlesHelper.getMessageBundles("delete.person.confirmation.question.text"), main.getPrimaryStage());
    	if (requestConfirmation.get() == ButtonType.OK) { 
	    	PersonMapper personMapper = main.getPersonMapper();
	    	personTable.getItems().remove(person);
			personMapper.deleteById(person.getId());
    	}
    }
    
    void updatePerson(Person person) {
    	PersonMapper personMapper = main.getPersonMapper();
    	personMapper.update(person);
    }
    
    @FXML
    void uploadImage() {
    	PersonMapper personMapper = main.getPersonMapper();
    	Person selectedItem = personTable.getSelectionModel().getSelectedItem();
    	if (selectedItem != null) {
	    	File file = fileChooser.showOpenDialog(main.getPrimaryStage());
	    	if (file != null) {
	    		byte[] bytes = ImageUtil.convertToByte(file.getAbsolutePath());
	    		// only update image if the new one is valid
	    		if (bytes.length>  0) {
					selectedItem.setImage(bytes);
		    		personMapper.update(selectedItem);
		    		showPersonDetails(selectedItem);
	    		}
	    	}
    	}
    }
    
    @FXML
    void printPerson() {
        URL reportStream = main.getJasperResource();  
		try {
			
			JasperReport jr = (JasperReport) JRLoader.loadObject(reportStream);

			// Create the JasperPrint object
			// Make sure to pass the JasperReport, report parameters, and data
			// source
			
			Person selectedItem = personTable.getSelectionModel().getSelectedItem();
			
			Map<String, Object> mapParameters = new HashMap<String, Object>();
			if (selectedItem != null) {
				mapParameters.put("PERSON_ID", selectedItem.getId());
				mapParameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, ResourceBundlesHelper.getBundle());
				mapParameters.put(JRParameter.REPORT_LOCALE, ResourceBundlesHelper.getBundle().getLocale());
			}
			BorderPane progressPane = createLoadingPane();
			progressPane.getStyleClass().add("progressPaneBackground");
			stackPane.getChildren().add(progressPane);
			new Thread(new Runnable() {
				JasperViewer jv ;
				@Override
				public void run() {
					try {
						JasperPrint jp = JasperFillManager.fillReport(jr,
								mapParameters, main.getConnection());

						jv = new JasperViewer(jp, false);
					} catch (JRException e) {
						ExceptionHandler.showErrorAndLog(
								"Can not open Jasper report", e);
					} finally {
						Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								stackPane.getChildren().remove(progressPane);
								if (jv != null) {
									jv.setVisible(true);
								}
							}
						});
					}
					
				}
			}).start();
			
			
			
		} catch (JRException e) {
        	ExceptionHandler.showErrorAndLog("Can not open Jasper report", e);
        }
    }
    
    /**
     * 
     * @return the item which is currently selected by user
     */
    public Person getCurrentSelected() {
		return currentSelected;
	}

    /**
     * Select in the table view the person {@code person}
     * @param person
     */
	public void selectPerson(Person person) {
		if (person == null) return;
		// search for this person in table data
		for (Person p : personTable.getItems()) {
			if (p.getId() == person.getId()) {
				personTable.getSelectionModel().select(p);
			}
		}
	}
	
	public static BorderPane createLoadingPane() {
		ProgressIndicator progressBar = new ProgressIndicator();
		progressBar.setPrefSize(100, 100);
		Group progressGroup= new Group(progressBar);
		BorderPane progressPane = new BorderPane(progressGroup);
		return progressPane;
	}

}
