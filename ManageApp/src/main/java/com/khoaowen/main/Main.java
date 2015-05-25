package com.khoaowen.main;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Locale;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.Level;

import com.khoaowen.main.dao.MyBatisConnectionFactory;
import com.khoaowen.main.mapper.PersonMapper;
import com.khoaowen.main.model.Person;
import com.khoaowen.main.view.MainFrameController;
import com.khoaowen.main.view.RootLayoutController;
import com.khoaowen.utils.Constants;
import com.khoaowen.utils.ExceptionHandler;
import com.khoaowen.utils.ResourceBundlesHelper;


public class Main extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	private MyBatisConnectionFactory factory;
	private PersonMapper personMapper;
	private MainFrameController mainFrameController;
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler::showError);
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(Constants.MANAGE_APP_TITLE);
		this.primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("icon/application.png")));
		
		ResourceBundlesHelper.setLocale(new Locale("vi","VN"));
		
		initRootLayout();
		ExceptionHandler.log(Level.INFO, "Manage App starts...");
	}
	
	

	/**
	 * Initializes the root layout
	 */
	private void initRootLayout() {
		try {
			
			// Load root layout from fxml file.
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/RootLayout.fxml"));
            loader.setResources(ResourceBundlesHelper.getBundle());
            rootLayout = loader.load();
            
            //Give the controller access to main app
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch (Exception e) {
			ExceptionHandler.showErrorAndLog(e);
		}
	}
	
	/**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview(Person person) {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/MainFrame.fxml"));
            loader.setResources(ResourceBundlesHelper.getBundle());
            AnchorPane listPerson = loader.load();
            // Set person overview into the center of root layout.
            rootLayout.setCenter(listPerson);
            
            //Give the controller access to main app
            mainFrameController = loader.getController();
            mainFrameController.setMainApp(this);
            mainFrameController.selectPerson(person);
        } catch (IOException e) {
        	ExceptionHandler.showErrorAndLog(e);
        }
    }
    
    /**
     * 
     * @return the loader for dialog "about information"
     */
    public FXMLLoader getAboutInformationLoader() {
    	FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/About.fxml"));
        loader.setResources(ResourceBundlesHelper.getBundle());
        return loader;
    }
    
    /**
     * 
     * @return the resource for creating report
     */
    public URL getJasperResource() {
    	return this.getClass().getResource("jasper/exportFormula.jasper");
    }
    
	/**
	 * Changes the database
	 * 
	 * @param filePath
	 *            the location of the database
	 * @param createNew
	 *            create a new data base if {@code true}. Otherwise just open a
	 *            new session to it
	 */
    public void swapDatabase(String filePath, boolean createNew) {
    	if (factory != null) {
    		factory.closeSession();
    	}
    	factory = new MyBatisConnectionFactory(
				filePath);
		SqlSession session = factory.openSession();
		personMapper =  session.getMapper(PersonMapper.class);
		if (createNew) {
			// create a new empty table
			session.update("createTable");
		}
		this.primaryStage.setTitle(Constants.MANAGE_APP_TITLE + " (" + filePath + Constants.DATABASE_EXT+")");
		showPersonOverview(null);
    }

	@Override
	public void stop() throws Exception {
		if (factory != null) {
			factory.closeSession();
		}
	}
	
	public PersonMapper getPersonMapper() {
		return personMapper;
	}
	
	/**
	 * 
	 * @return
	 */
	public Connection getConnection() {
		return factory.getConnection();
	}

	public void refreshGui() {
		boolean initContent = false;
		Person currentPerson = null;
		if (((BorderPane)this.primaryStage.getScene().getRoot()).getCenter() != null) {
			initContent = true;
			currentPerson = mainFrameController.getCurrentSelected();
		}
		backupUserPrefs();
		((BorderPane)this.primaryStage.getScene().getRoot()).getChildren().clear();
		initRootLayout();
		if (initContent) {
			showPersonOverview(currentPerson);
		}
		restoreUserPrefs();
	}

	private void restoreUserPrefs() {
		Preferences userPrefs = Preferences.userNodeForPackage(getClass());
	    // get window location from user preferences: use x=100, y=100, width=400, height=400 as default
	    double x = userPrefs.getDouble(Constants.STAGE_X, 100);
	    double y = userPrefs.getDouble(Constants.STAGE_Y, 100);
	    double w = userPrefs.getDouble(Constants.STAGE_WIDTH, 400);
	    double h = userPrefs.getDouble(Constants.STAGE_HEIGHT, 400);
	    
	    primaryStage.setX(x);
	    primaryStage.setY(y);
	    primaryStage.setWidth(w);
	    primaryStage.setHeight(h);
		
	}

	private void backupUserPrefs() {
		Preferences userPrefs = Preferences.userNodeForPackage(getClass());
	    userPrefs.putDouble(Constants.STAGE_X, primaryStage.getX());
	    userPrefs.putDouble(Constants.STAGE_Y, primaryStage.getY());
	    userPrefs.putDouble(Constants.STAGE_WIDTH, primaryStage.getWidth());
	    userPrefs.putDouble(Constants.STAGE_HEIGHT, primaryStage.getHeight());
	}
	
	
}
