package com.example.antrojiprogramavimopraktika.Controller;

import com.example.antrojiprogramavimopraktika.Utils.SceneLoader;
import java.io.IOException;
import javafx.stage.Stage;

public class MainController {

    private MainController() {}

    public static void ShowAdminPanel(Stage currentStage) throws IOException {
        SceneLoader.load(
            currentStage,
            "/com/example/antrojiprogramavimopraktika/View/AdminPanel.fxml"
        );
    }
}
