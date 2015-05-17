package com.khoaowen.main;


import java.io.IOException;
import java.sql.Connection;
import java.util.Locale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.apache.ibatis.session.SqlSession;

import com.khoaowen.main.dao.MyBatisConnectionFactory;
import com.khoaowen.main.mapper.PersonMapper;
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
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler::showError);
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(Constants.MANAGE_APP_TITLE);
		this.primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("icon/application.png")));
		
//		ResourceBundlesHelper.setLocale(Locale.getDefault());
		ResourceBundlesHelper.setLocale(new Locale("vi","VN"));
		
		initRootLayout();
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
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/MainFrame.fxml"));
            loader.setResources(ResourceBundlesHelper.getBundle());
            AnchorPane listPerson = loader.load();
            // Set person overview into the center of root layout.
            rootLayout.setCenter(listPerson);
            
            //Give the controller access to main app
            MainFrameController controller = loader.getController();
            controller.setMainApp(this);
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
		showPersonOverview();
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
}
