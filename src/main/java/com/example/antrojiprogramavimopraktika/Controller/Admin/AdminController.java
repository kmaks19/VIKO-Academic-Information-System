package com.example.antrojiprogramavimopraktika.Controller.Admin;

import com.example.antrojiprogramavimopraktika.Controller.MainController;
import com.example.antrojiprogramavimopraktika.Entities.Person;
import com.example.antrojiprogramavimopraktika.Entities.Session;
import com.example.antrojiprogramavimopraktika.Utils.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public final class AdminController {

    @FXML
    private Button admin_stgrupesBtn;

    @FXML
    private Button admin_destytojaiBtn;

    @FXML
    private Button admin_studentaiBtn;

    @FXML
    private Button admin_ddalykaiBtn;

    @FXML
    private AnchorPane admin_contentArea;

    private Person person;

    @FXML
    private Button adminpanel_logoutBtn;

    public void initialize() {
        person = Session.getInstance().getCurrentUserInstance();

        if (person != null) {
            System.out.println("UserID: " + person.getUserID() + " has logged in as " + person.getRole());
        }

        Map<Button, String> buttonsMap = Map.ofEntries(
                Map.entry(admin_stgrupesBtn, "/com/example/antrojiprogramavimopraktika/View/Admin/StudentGroups.fxml"),
                Map.entry(admin_destytojaiBtn, "/com/example/antrojiprogramavimopraktika/View/Admin/TeachersControl.fxml"),
                Map.entry(admin_studentaiBtn, "/com/example/antrojiprogramavimopraktika/View/Admin/Students.fxml"),
                Map.entry(admin_ddalykaiBtn, "/com/example/antrojiprogramavimopraktika/View/Admin/Subjects.fxml")
        );

        for(Map.Entry<Button, String> entry : buttonsMap.entrySet()) {
            Button button = entry.getKey();

            String viewPath = entry.getValue();

            button.setOnAction(_ -> loadView(viewPath));
        }

        adminpanel_logoutBtn.setOnAction(_ -> logout());
    }

    private void logout() {
        Session.getInstance().setCurrentUser(null);

        try {
            MainController.ShowLoginPanel(
                    (Stage) adminpanel_logoutBtn.getScene().getWindow()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadView(String fxmlPath) {
        try {
            SceneLoader.loadAnchored(fxmlPath, admin_contentArea);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
