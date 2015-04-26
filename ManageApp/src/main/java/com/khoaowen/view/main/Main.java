package com.khoaowen.view.main;


import java.util.Locale;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.apache.logging.log4j.Level;

import com.khoaowen.utils.ExceptionHandler;
import com.khoaowen.utils.JfxUtils;
import com.khoaowen.utils.Resources;


public class Main extends Application
{
    public static void main( String[] args )
    {
    	launch(args);
    	
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try {
//			Resources.setLocale(Locale.getDefault());
			Resources.setLocale(new Locale("vi","VN"));
			Scene scene = new Scene((Parent)JfxUtils.loadFxml("MainFrame.fxml"));
			primaryStage.setScene(scene);
			primaryStage.setTitle("Manage App");
			primaryStage.show();
			
		} catch (Exception e) {
			String messageError = ExceptionHandler.getStackTrace(e);
			ExceptionHandler.showError(messageError);
			ExceptionHandler.logger.log(Level.FATAL, messageError);
		}
		
	}
	
}
