package com.example.antrojiprogramavimopraktika.Controller.Teacher;

import com.example.antrojiprogramavimopraktika.Controller.MainController;
import com.example.antrojiprogramavimopraktika.Entities.Grade;
import com.example.antrojiprogramavimopraktika.Entities.Person;
import com.example.antrojiprogramavimopraktika.Entities.Session;
import com.example.antrojiprogramavimopraktika.Interfaces.IGradeRepository;
import com.example.antrojiprogramavimopraktika.Repositories.GradeRepository;
import com.example.antrojiprogramavimopraktika.Utils.SceneLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public final class GradeController {

    @FXML
    private TableView<Grade> grades_table;

    @FXML
    private TableColumn<Grade, Integer> grades_id;

    @FXML
    private TableColumn<Grade, String> grades_firstname;

    @FXML
    private TableColumn<Grade, String> grades_lastname;

    @FXML
    private TableColumn<Grade, String> grades_subject;

    @FXML
    private TableColumn<Grade, Integer> grades_grade;

    @FXML
    private TableColumn<Grade, LocalDate> grades_date;

    @FXML
    private Button grades_addGradeBtn;

    @FXML
    private Button grades_editGradeBtn;

    @FXML
    private Button grades_logout;

    private Person person;

    private final IGradeRepository gradeRepo = new GradeRepository();

    public void initialize() {
        person = Session.getInstance().getCurrentUserInstance();

        if (person != null) {
            System.out.println("UserID: " + person.getUserID() + " has logged in as " + person.getRole());
        }

        grades_id.setCellValueFactory(new PropertyValueFactory<>("id"));

        grades_firstname.setCellValueFactory(
                cell -> new SimpleStringProperty(cell.getValue().getStudent().getFirstName())
        );

        grades_lastname.setCellValueFactory(
                cell -> new SimpleStringProperty(cell.getValue().getStudent().getLastName())
        );

        grades_subject.setCellValueFactory(
                cell -> new SimpleStringProperty(cell.getValue().getSubject().getName())
        );

        grades_grade.setCellValueFactory(new PropertyValueFactory<>("grade"));

        grades_date.setCellValueFactory(new PropertyValueFactory<>("gradeDate"));

        loadGrades(Session.getInstance().getCurrentUserInstance().getUserID());

        grades_addGradeBtn.setOnAction(_ -> {
            try {
                SceneLoader.loadModal(
                        "/com/example/antrojiprogramavimopraktika/View/Teacher/Modals/AddGradeModal.fxml",
                        "Pažymio pridėjimas",
                        modalController -> ((AddGradeController) modalController).setParent(this)
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        grades_editGradeBtn.setOnAction(_ -> {
            try {
                SceneLoader.loadModal(
                        "/com/example/antrojiprogramavimopraktika/View/Teacher/Modals/ChangeGradeModal.fxml",
                        "Pažymio redagavimas",
                        modalController -> ((ChangeGradeController) modalController).setParent(this)
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        grades_logout.setOnAction(_ -> logout());
    }

    public void loadGrades(int teacherID) {
        List<Grade> grades = gradeRepo.getGradeListForTeacher(teacherID);
        grades_table.setItems(FXCollections.observableArrayList(grades));
    }

    private void logout() {
        Session.getInstance().setCurrentUser(null);

        try {
            MainController.ShowLoginPanel(
                    (Stage) grades_logout.getScene().getWindow()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
