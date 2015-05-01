package com.khoaowen.utils;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import com.khoaowen.main.Main;

public class JfxUtils {
	 
    public static Node loadFxml(String fxml) {
        FXMLLoader loader = new FXMLLoader();
        try {
            loader.setLocation(JfxUtils.class.getResource(fxml));
            loader.setResources(Resources.getBundle());
            Node root = (Node) loader.load(Main.class.getResource(fxml).openStream());
            return root;
        } catch (IOException e) {
            throw new IllegalStateException("cannot load FXML screen", e);
        }
    }
}