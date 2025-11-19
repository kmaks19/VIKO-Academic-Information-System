package com.example.antrojiprogramavimopraktika.Controller;

import com.example.antrojiprogramavimopraktika.Utils.SceneLoader;
import java.io.IOException;
import javafx.stage.Stage;

public class MainController {

    private MainController() {}

    public static void ShowAdminPanel(Stage currentStage) throws IOException {
        SceneLoader.load(
            currentStage,
                "/com/example/antrojiprogramavimopraktika/View/Admin/AdminPanel.fxml"
        );
    }

    public static void ShowLoginPanel(Stage currentStage) throws IOException {
        SceneLoader.load(
                currentStage,
                "/com/example/antrojiprogramavimopraktika/View/AuthView.fxml"
        );
    }

    public static void ShowTeacherPanel(Stage currentStage) throws IOException {
        SceneLoader.load(
                currentStage,
                "/com/example/antrojiprogramavimopraktika/View/Teacher/Teacher.fxml"
        );
    }

    public static void ShowStudentPanel(Stage currentStage) throws IOException {
        SceneLoader.load(
                currentStage,
                "/com/example/antrojiprogramavimopraktika/View/Student/Student.fxml"
        );
    }

}
