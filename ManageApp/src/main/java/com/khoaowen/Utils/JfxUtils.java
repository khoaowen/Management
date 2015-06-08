package com.khoaowen.utils;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.khoaowen.main.Main;

public class JfxUtils {
	
	public static DropShadow borderGlow = new DropShadow();
	{	borderGlow.setColor(Color.BLUE);
		borderGlow.setOffsetX(3f);
		borderGlow.setOffsetY(3f);
		borderGlow.setWidth(10);
		borderGlow.setHeight(10);
	}
	
    public static Node loadFxml(String fxml) {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(JfxUtils.class.getResource(fxml));
            loader.setResources(ResourceBundlesHelper.getBundle());
            Node root = (Node) loader.load(Main.class.getResource(fxml).openStream());
            return root;
        } catch (IOException e) {
            throw new IllegalStateException("cannot load FXML screen", e);
        }
    }
    
	/**
	 * Configures the interface of file chooser
	 * 
	 * @param fileChooser
	 * @param title
	 *            title of the dialog
	 * @param extensions
	 *            filter file extensions available for user to choose. The
	 *            format for each filter is a {@link Pair} which is (
	 *            {@code file extension description} ,
	 *            {@code regex for file extension}) <br>
	 *            For example: (All images , *.*), (JPG, *.jpg)...
	 */
    public static void configureFileChooser(final FileChooser fileChooser, String title, Pair<String, String>... extensions) {
		fileChooser.setTitle(ResourceBundlesHelper.getMessageBundles("imageChooser.title.text"));
		fileChooser.setInitialDirectory(new File(System
				.getProperty("user.home")));
		for (Pair<String, String> pair : extensions) {
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(pair.getLeft(), pair.getRight()));
		}
	}
    
    /**
     * Normalize characters from the string {@code s} for comparison
     * @param s
     * @return
     */
    public static String normalizeStringToCompare(String s) {
    	if (s == null) {
    		return "";
    	}
    	String result;
    	result = ExtFunc.removeAccent(StringUtils.lowerCase(StringUtils.stripAccents(s)));
    	return result;
    }
    
}