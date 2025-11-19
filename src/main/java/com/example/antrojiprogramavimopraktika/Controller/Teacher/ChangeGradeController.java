package com.example.antrojiprogramavimopraktika.Controller.Teacher;

import com.example.antrojiprogramavimopraktika.Entities.Grade;
import com.example.antrojiprogramavimopraktika.Entities.Session;
import com.example.antrojiprogramavimopraktika.Interfaces.IGradeRepository;
import com.example.antrojiprogramavimopraktika.Repositories.GradeRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.List;

public class ChangeGradeController {
    public ChangeGradeController() {}

    private GradeController parent;

    public void setParent(GradeController parent) {
        this.parent = parent;
    }

    private final IGradeRepository gradeRepo = new GradeRepository();

    @FXML
    private ChoiceBox<Grade> editGrade_selectfromgradeList;

    @FXML
    private ChoiceBox<Integer> editGrade_selectGrade;

    @FXML
    private Label editGrade_errorLabel;

    @FXML
    private Button editGrade_submitBtn;

    @FXML
    private Button editGrade_cancelBtn;

    public void initialize() {
        editGrade_cancelBtn.setOnAction(_ -> close());

        loadGrades(Session.getInstance().getCurrentUserInstance().getUserID());

        editGrade_selectfromgradeList.getSelectionModel().selectedItemProperty().addListener((_, _, newGrade) -> {
            if(newGrade != null) {
                showGradeSelection();
            }
        });

        editGrade_submitBtn.setOnAction(_ -> {
            if(save()) {
                if(parent != null) {
                    parent.loadGrades(Session.getInstance().getCurrentUserInstance().getUserID());
                }
                close();
            }
        });

    }

    private void showGradeSelection() {
        editGrade_selectGrade.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    private void loadGrades(int teacherID) {
        List<Grade> grades = gradeRepo.getGradeListForTeacher(teacherID);
        editGrade_selectfromgradeList.setItems(FXCollections.observableArrayList(grades));

        editGrade_selectfromgradeList.setConverter(new StringConverter<>() {
            @Override
            public String toString(Grade grade) {
                if(grade == null) return "";
                return grade.getId() + ". "
                        + grade.getStudent().getFullName() + " — "
                        + grade.getSubject().getName() + " | "
                        + grade.getGrade();
            }

            @Override
            public Grade fromString(String s) {
                return null;
            }
        });
    }

    private boolean save() {
        editGrade_errorLabel.setVisible(false);

        Grade selected = editGrade_selectfromgradeList.getValue();
        Integer selectedGrade = editGrade_selectGrade.getValue();

        int gradeID = selected.getId();

        if(selected == null) {
            editGrade_errorLabel.setText("Privalote pasirinkti iš pirminio sąrašo!");
            editGrade_errorLabel.setVisible(true);
            return false;
        }
        if(selectedGrade == null) {
            editGrade_errorLabel.setText("Privalote pasirinkti pažymį!");
            editGrade_errorLabel.setVisible(true);
            return false;
        }


        boolean success = gradeRepo.updateGrade(gradeID, selectedGrade);

        if(!success) {
            editGrade_errorLabel.setText("Nepavyko pakeisti pažymio!");
            editGrade_errorLabel.setVisible(true);
            return false;
        }

        selected.setGrade(selectedGrade);

        return true;
    }

    private void close() {
        ((Stage) editGrade_cancelBtn.getScene().getWindow()).close();
    }
}
