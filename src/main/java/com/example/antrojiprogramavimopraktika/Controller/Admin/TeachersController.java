package com.example.antrojiprogramavimopraktika.Controller.Admin;

import com.example.antrojiprogramavimopraktika.Entities.Teacher;
import com.example.antrojiprogramavimopraktika.Entities.TeacherSubject;
import com.example.antrojiprogramavimopraktika.Repositories.TeacherSubjectRepository;
import com.example.antrojiprogramavimopraktika.Repositories.UserRepository;
import com.example.antrojiprogramavimopraktika.Utils.SceneLoader;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public final class TeachersController {

    // Techars List variables
    @FXML
    private TableView<Teacher> teacherlist_table;

    @FXML
    private TableColumn<Teacher, Integer> teacherslist_id;

    @FXML
    private TableColumn<Teacher, String> teacherslist_firstname;

    @FXML
    private TableColumn<Teacher, String> teacherslist_lastname;

    @FXML
    private TableColumn<Teacher, String> teacherslist_email;

    @FXML
    private TableColumn<Teacher, String> teachers_listbirthdate;

    @FXML
    private Button teacherslist_addteacherBtn;

    @FXML
    private Button teacherslist_removeteacherBtn;

    // Teacher Subjects List variables
    @FXML
    private TableView<TeacherSubject> subjectslist_table;

    @FXML
    private TableColumn<TeacherSubject, Integer> subjectslist_id;

    @FXML
    private TableColumn<TeacherSubject, String> subjectslist_sname;

    @FXML
    private TableColumn<TeacherSubject, String> subjectslist_firstname;

    @FXML
    private TableColumn<TeacherSubject, String> subjectslist_lastname;

    @FXML
    private Button subjectslist_addBtn;

    @FXML
    private Button subjectslist_removeBtn;

    private final UserRepository userRepo = new UserRepository();
    private final TeacherSubjectRepository tsRepo = new TeacherSubjectRepository();

    public void initialize() {
        // Teacher List
        teacherslist_id.setCellValueFactory(new PropertyValueFactory<>("userID"));
        teacherslist_firstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        teacherslist_lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        teachers_listbirthdate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        teacherslist_email.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Teacher Subjects List

        subjectslist_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        subjectslist_sname.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        subjectslist_firstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        subjectslist_lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        loadTeachers();
        loadTeacherSubjects();

        // Remove teacher button
        teacherslist_removeteacherBtn.setOnAction(_ -> {
            Teacher selected = teacherlist_table.getSelectionModel().getSelectedItem();

            if(selected == null) return;

            userRepo.removeUser(selected.getUserID());

            update();
        });

        teacherslist_addteacherBtn.setOnAction(_ -> {
            try {
                SceneLoader.loadModal(
                        "/com/example/antrojiprogramavimopraktika/View/Admin/Modals/AddTeacherUserModal.fxml",
                        "Dėstytojo pridėjimas",
                        modalController -> ((AddTeacherController) modalController).setParent(this)
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void update() {
        loadTeachers();
        loadTeacherSubjects();
    }

    public void loadTeachers() {
        List<Teacher> teachers = userRepo.getAllTeachers();
        teacherlist_table.setItems(FXCollections.observableArrayList(teachers));
    }

    public void loadTeacherSubjects() {
        List<TeacherSubject> tSubjects = tsRepo.getTeacherSubjects();
        subjectslist_table.setItems(FXCollections.observableArrayList(tSubjects));
    }

}
