package com.khoaowen.main;


import java.io.IOException;
import java.util.Locale;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.khoaowen.main.model.Person;
import com.khoaowen.main.view.MainFrameController;
import com.khoaowen.utils.ExceptionHandler;
import com.khoaowen.utils.ResourceBundlesHelper;


public class Main extends Application {
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Manage App");
		
		ResourceBundlesHelper.setLocale(Locale.getDefault()); // new Locale("vi","VN")
		
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
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = FXMLLoader.load(loader.getLocation(), ResourceBundlesHelper.getBundle());
			
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
            loader.setLocation(Main.class.getResource("view/MainFrame.fxml"));
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
	
	public static void main( String[] args ) {
    	launch(args);
    	
    }

	public ObservableList<Person> getPersonData() {
		return FXCollections.emptyObservableList();
	}
	
}
