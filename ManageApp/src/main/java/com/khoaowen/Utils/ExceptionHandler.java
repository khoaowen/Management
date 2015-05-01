package com.khoaowen.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class ExceptionHandler implements Thread.UncaughtExceptionHandler{

	public static final Logger logger = LogManager.getLogger("MainApp");
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		// this is not called by Java FX
//		String messageError = getStackTrace(e);
//		logger.log(Level.FATAL, messageError);
//		JOptionPane.showMessageDialog(null, messageError);
	}
	
	
	public static String getStackTrace(Throwable aThrowable) {
	    final Writer result = new StringWriter();
	    final PrintWriter printWriter = new PrintWriter(result);
	    aThrowable.printStackTrace(printWriter);
	    return result.toString();
	  }
	
	/**
	 * Show the error with stack trace to user and log this stack trace
	 * @param e exception which supplies the stack trace
	 */
	public static void showErrorAndLog(Exception e) {
		String messageError = getStackTrace(e);
		showError(messageError);
		ExceptionHandler.logger.log(Level.FATAL, messageError);
	}
	
	public static void showError(String stack) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(Resources.getMessageBundles("errorDialog.text"/*,new Locale("vi","VN")*/));
		
//		Enumeration e = Logger.getRootLogger().getAllAppenders();
//	    while ( e.hasMoreElements() ){
//	      Appender app = (Appender)e.nextElement();
//	      if ( app instanceof FileAppender ){
//	        System.out.println("File: " + ((FileAppender)app).getFile());
//	      }
//	    }
		alert.setHeaderText(StringEscapeUtils.unescapeJava(Resources.getMessageBundles("anErrorOccurs.text")));
		alert.setContentText(Resources.getMessageBundles("errorDialogFurtherInfo.text") + System.getProperty("user.dir")+System.getProperty("file.separator")+"manageAppLogs"+System.getProperty("file.separator")+"app.log");


		Label label = new Label(Resources.getMessageBundles("exceptionStackTrace.text"));

		TextArea textArea = new TextArea(stack);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);
		alert.getDialogPane().setExpanded(true);

		alert.show();
		
	}
}
