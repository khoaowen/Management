package com.khoaowen.main.view;

import java.io.File;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

import org.apache.commons.lang3.tuple.Pair;

import com.khoaowen.main.Main;
import com.khoaowen.utils.Constants;
import com.khoaowen.utils.FilenameUtils;
import com.khoaowen.utils.JfxUtils;
import com.khoaowen.utils.ResourceBundlesHelper;

public class RootLayoutController {
	
	private FileChooser fileChooser = new FileChooser();
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
		JfxUtils.configureFileChooser(fileChooser, "databaseChooser.title.text", 
	    		Pair.of(ResourceBundlesHelper.getMessageBundles("database.description.text"), Constants.DATABASE_EXT_REGEX));
	}
	
	@FXML
	private void newDatabase() {
		fileChooser.setTitle(ResourceBundlesHelper.getMessageBundles("newDatabaseChooser.title.text"));
		File file = fileChooser.showSaveDialog(main.getPrimaryStage());
		if (file != null) {
			main.swapDatabase(FilenameUtils.removeExtension(FilenameUtils.removeExtension(file.getAbsolutePath())), true);
		}
	}
	
	@FXML
	private void openDatabase() {
		fileChooser.setTitle(ResourceBundlesHelper.getMessageBundles("newDatabaseChooser.title.text"));
		File file = fileChooser.showOpenDialog(main.getPrimaryStage());
		if (file != null) {
			main.swapDatabase(FilenameUtils.removeExtension(FilenameUtils.removeExtension(file.getAbsolutePath())), false);
		}
	}
	
	@FXML
	private void closeApplication() {
		Platform.exit();
	}
}
