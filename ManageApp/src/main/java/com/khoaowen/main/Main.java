package com.khoaowen.main;


import java.io.IOException;
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
import com.khoaowen.utils.ExceptionHandler;
import com.khoaowen.utils.ResourceBundlesHelper;


public class Main extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	private SqlSession session;
	private PersonMapper personMapper;
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler::showError);
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Manage App");
		this.primaryStage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("icon/application.ico")));
		
//		ResourceBundlesHelper.setLocale(Locale.getDefault());
		ResourceBundlesHelper.setLocale(new Locale("vi","VN"));
		
		MyBatisConnectionFactory factory = new MyBatisConnectionFactory(
				"C:\\Users\\owen\\Desktop\\test");
		session = factory.openSession();
		personMapper =  session.getMapper(PersonMapper.class);
		initRootLayout();
		showPersonOverview();
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

	@Override
	public void stop() throws Exception {
		session.close();
	}
	
	public PersonMapper getPersonMapper() {
		return personMapper;
	}
}
