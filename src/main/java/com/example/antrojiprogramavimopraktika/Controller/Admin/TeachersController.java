package com.example.antrojiprogramavimopraktika.Controller.Admin;

import com.example.antrojiprogramavimopraktika.Controller.Admin.ModalControllers.AddTeacherController;
import com.example.antrojiprogramavimopraktika.Controller.Admin.ModalControllers.AddTeacherToSubjectController;
import com.example.antrojiprogramavimopraktika.Entities.Teacher;
import com.example.antrojiprogramavimopraktika.Entities.TeacherSubject;
import com.example.antrojiprogramavimopraktika.Interfaces.ITeacherRepository;
import com.example.antrojiprogramavimopraktika.Interfaces.ITeacherSubjectRepository;
import com.example.antrojiprogramavimopraktika.Interfaces.IUserRepository;
import com.example.antrojiprogramavimopraktika.Repositories.TeacherRepository;
import com.example.antrojiprogramavimopraktika.Repositories.TeacherSubjectRepository;
import com.example.antrojiprogramavimopraktika.Repositories.UserRepository;
import com.example.antrojiprogramavimopraktika.Utils.SceneLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
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
    private TableColumn<Teacher, LocalDate> teachers_listbirthdate;

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

    private final IUserRepository userRepo = new UserRepository();
    private final ITeacherSubjectRepository tsRepo = new TeacherSubjectRepository();
    private final ITeacherRepository teacherRepo = new TeacherRepository();

    public void initialize() {
        // Teacher List
        teacherslist_id.setCellValueFactory(new PropertyValueFactory<>("userID"));
        teacherslist_firstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        teacherslist_lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        teachers_listbirthdate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        teacherslist_email.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Teacher Subjects List

        subjectslist_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        subjectslist_sname.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getSubject().getName())
        );
        subjectslist_firstname.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getTeacher().getFirstName())
        );
        subjectslist_lastname.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getTeacher().getLastName())
        );

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

        subjectslist_addBtn.setOnAction(_ -> {
            try {
                SceneLoader.loadModal(
                        "/com/example/antrojiprogramavimopraktika/View/Admin/Modals/AddTeacherSubjectModal.fxml",
                        "Dėstytojo pridėjimas",
                        modalController -> ((AddTeacherToSubjectController) modalController).setParent(this)
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        subjectslist_removeBtn.setOnAction(_ -> {
            TeacherSubject selected = subjectslist_table.getSelectionModel().getSelectedItem();

            if(selected == null) return;

            tsRepo.removeTeacherSubject(selected.getId());

            loadTeacherSubjects();
        });
    }

    public void update() {
        loadTeachers();
        loadTeacherSubjects();
    }

    public void loadTeachers() {
        List<Teacher> teachers = teacherRepo.getAllTeachers();
        teacherlist_table.setItems(FXCollections.observableArrayList(teachers));
    }

    public void loadTeacherSubjects() {
        List<TeacherSubject> tSubjects = tsRepo.getTeacherSubjects();
        subjectslist_table.setItems(FXCollections.observableArrayList(tSubjects));
    }

}
