package com.example.antrojiprogramavimopraktika.Controller.Student;

import com.example.antrojiprogramavimopraktika.Controller.MainController;
import com.example.antrojiprogramavimopraktika.Entities.Grade;
import com.example.antrojiprogramavimopraktika.Entities.Session;
import com.example.antrojiprogramavimopraktika.Interfaces.IGradeRepository;
import com.example.antrojiprogramavimopraktika.Repositories.GradeRepository;
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

public final class StudentController {

    @FXML
    private TableView<Grade> studentgrades_table;

    @FXML
    private TableColumn<Grade, Integer> studentgrades_id;

    @FXML
    private TableColumn<Grade, String> studentgrades_subject;

    @FXML
    private TableColumn<Grade, Integer> studentgrades_grade;

    @FXML
    private TableColumn<Grade, LocalDate> studentgrades_gradedate;

    @FXML
    private Button studentgrades_logout;

    private final IGradeRepository gradeRepo = new GradeRepository();

    public void initialize() {
        studentgrades_logout.setOnAction(_ -> logout());

        studentgrades_id.setCellValueFactory(new PropertyValueFactory<>("id"));

        studentgrades_subject.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        cell.getValue().getSubject().getName()
                )
        );

        studentgrades_grade.setCellValueFactory(
                new PropertyValueFactory<>("grade")
        );

        studentgrades_gradedate.setCellValueFactory(
                new PropertyValueFactory<>("gradeDate")
        );

        loadGrades();
    }

    private void loadGrades() {
        int studentID = Session.getInstance().getCurrentUserInstance().getUserID();
        List<Grade> grades = gradeRepo.getGradeListForStudent(studentID);
        studentgrades_table.setItems(FXCollections.observableArrayList(grades));
    }

    private void logout() {
        Session.getInstance().setCurrentUser(null);

        try {
            MainController.ShowLoginPanel(
                    (Stage) studentgrades_logout.getScene().getWindow()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
