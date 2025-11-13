package com.example.antrojiprogramavimopraktika.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public final class SceneLoader {

    private SceneLoader() {}

    private final static String TITLE = "Studentų informacinė sistema";
    private final static int WIDTH = 1280;
    private final static int HEIGHT = 960;

    public static void load(final Stage stage, final String fxmlPath) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(SceneLoader.class.getResource(fxmlPath));

        Scene scene =  new Scene(fxmlLoader.load(), WIDTH, HEIGHT);

        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    public static void loadAnchored(String fxmlPath, AnchorPane parent) throws Exception {
        FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource(fxmlPath));
        Parent pane = loader.load();

        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);

        parent.getChildren().setAll(pane);
    }
}