package com.khoaowen.main.view;

import java.io.File;
import java.util.Locale;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import org.apache.commons.lang3.tuple.Pair;

import com.khoaowen.main.Main;
import com.khoaowen.utils.Constants;
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
		setVietnameseLanguage();
		vnMenu.setGraphic(new ImageView(vnFlagImage));
		enMenu.setGraphic(new ImageView(enFlagImage));
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
	
	@FXML
	private void setVietnameseLanguage() {
		ResourceBundlesHelper.setLocale(new Locale("vi","VN"));
		languageMenu.setGraphic(new ImageView(vnFlagImage));
	}
	
	@FXML
	private void setEnglishLanguage() {
		ResourceBundlesHelper.setLocale(Locale.ENGLISH);
		languageMenu.setGraphic(new ImageView(enFlagImage));
	}
}
