package com.example.antrojiprogramavimopraktika;

import com.example.antrojiprogramavimopraktika.Database.Database;
import com.example.antrojiprogramavimopraktika.Utils.SceneLoader;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        SceneLoader.load(
            stage,
            "/com/example/antrojiprogramavimopraktika/View/AuthView.fxml"
        );

        Database.createIfNotExists();
    }

    public static void main(String[] args) {
        launch();
    }
}
