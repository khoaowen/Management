package com.khoaowen.main.view;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.prefs.Preferences;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.apache.commons.lang3.tuple.Pair;

import com.khoaowen.main.Main;
import com.khoaowen.utils.Constants;
import com.khoaowen.utils.ExceptionHandler;
import com.khoaowen.utils.FilenameUtils;
import com.khoaowen.utils.ImageUtil;
import com.khoaowen.utils.JfxUtils;
import com.khoaowen.utils.ResourceBundlesHelper;

public class RootLayoutController {
	
	private static final int ICON_MENUBAR_SIZE = 16;
	@FXML
	private Menu languageMenu;
	@FXML
	private MenuItem vnMenu;
	@FXML
	private MenuItem enMenu;
	
	private FileChooser fileChooser = new FileChooser();
	private Main main;
	
	private Image vnFlagImage = ImageUtil.getImageResources("/icon/countries/vn.gif",ICON_MENUBAR_SIZE, ICON_MENUBAR_SIZE);
	private Image enFlagImage = ImageUtil.getImageResources("/icon/countries/us.gif",ICON_MENUBAR_SIZE, ICON_MENUBAR_SIZE);

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
		JfxUtils.configureFileChooser(fileChooser, "databaseChooser.title.text", 
	    		Pair.of(ResourceBundlesHelper.getMessageBundles("database.description.text"), Constants.DATABASE_EXT_REGEX));
		vnMenu.setGraphic(new ImageView(vnFlagImage));
		enMenu.setGraphic(new ImageView(enFlagImage));
		if (ResourceBundlesHelper.getBundle().getLocale().equals(new Locale("vi","VN"))) {
			languageMenu.setGraphic(new ImageView(vnFlagImage));
		} else {
			languageMenu.setGraphic(new ImageView(enFlagImage));
		}
	}
	
	@FXML
	private void newDatabase() {
		fileChooser.setTitle(ResourceBundlesHelper.getMessageBundles("newDatabaseChooser.title.text"));
		File file = fileChooser.showSaveDialog(main.getPrimaryStage());
		if (file != null) {
			main.swapDatabase(FilenameUtils.removeExtension(FilenameUtils.removeExtension(file.getAbsolutePath())), true);
			Preferences userPrefs = Preferences.userNodeForPackage(getClass());
		    userPrefs.put(Constants.DATABASE_DESTINATION_PREF, file.getParent());
		}
	}
	
	@FXML
	private void openDatabase() {
		Preferences userPrefs = Preferences.userNodeForPackage(getClass());
		fileChooser.setTitle(ResourceBundlesHelper.getMessageBundles("openDatabaseChooser.title.text"));
		File initialDirectory = new File(userPrefs.get(Constants.DATABASE_DESTINATION_PREF, System.getProperty("user.home")));
		if (initialDirectory.isDirectory()) {
			fileChooser.setInitialDirectory(initialDirectory);
		}
		File file = fileChooser.showOpenDialog(main.getPrimaryStage());
		if (file != null) {
			main.swapDatabase(FilenameUtils.removeExtension(FilenameUtils.removeExtension(file.getAbsolutePath())), false);
			userPrefs.put(Constants.DATABASE_DESTINATION_PREF, file.getParent());
		}
	}
	
	@FXML
	private void closeApplication() {
		Platform.exit();
	}
	
	@FXML
	private void setVietnameseLanguage() {
		ResourceBundlesHelper.setLocale(new Locale("vi","VN"));
		languageMenu.setGraphic(new ImageView(vnFlagImage));
		main.refreshGui();
	}
	
	@FXML
	private void setEnglishLanguage() {
		ResourceBundlesHelper.setLocale(Locale.US);
		languageMenu.setGraphic(new ImageView(enFlagImage));
		main.refreshGui();
	}
	
	@FXML
	private void showAboutInfo() {
		Stage dialog = new Stage();
		dialog.initStyle(StageStyle.UTILITY);
        AnchorPane info;
		try {
			//Give the controller access to main app
            
			FXMLLoader aboutInformationLoader = main.getAboutInformationLoader();
			info = aboutInformationLoader.load();
			AboutController controller = aboutInformationLoader.getController();
            controller.setMainApp(main);
            
			Scene scene = new Scene(info);
			dialog.setScene(scene);
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(main.getPrimaryStage());
			dialog.show();
		} catch (IOException e) {
			ExceptionHandler.showErrorAndLog("Can not show information dialog", e);
		}
		
	}
	
	/**
	 * Restore user preferences at dynamically language changes
	 */
	public void restoreUserPrefs() {
		Preferences userPrefs = Preferences.userNodeForPackage(getClass());
	    // get window location from user preferences: use x=100, y=100, width=400, height=400 as default
	    double x = userPrefs.getDouble(Constants.STAGE_X_PREF, 100);
	    double y = userPrefs.getDouble(Constants.STAGE_Y_PREF, 100);
	    double w = userPrefs.getDouble(Constants.STAGE_WIDTH_PREF, 400);
	    double h = userPrefs.getDouble(Constants.STAGE_HEIGHT_PREF, 400);
	    
	    main.getPrimaryStage().setX(x);
	    main.getPrimaryStage().setY(y);
	    main.getPrimaryStage().setWidth(w);
	    main.getPrimaryStage().setHeight(h);
		
	}
	/**
	 * Backup user preferences to restore at dynamically language changes
	 */
	public void backupUserPrefs() {
		Preferences userPrefs = Preferences.userNodeForPackage(getClass());
	    userPrefs.putDouble(Constants.STAGE_X_PREF, main.getPrimaryStage().getX());
	    userPrefs.putDouble(Constants.STAGE_Y_PREF, main.getPrimaryStage().getY());
	    userPrefs.putDouble(Constants.STAGE_WIDTH_PREF, main.getPrimaryStage().getWidth());
	    userPrefs.putDouble(Constants.STAGE_HEIGHT_PREF, main.getPrimaryStage().getHeight());
	    
	    
	}
	
}
