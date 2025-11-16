package com.example.antrojiprogramavimopraktika.Controller.Admin;

import com.example.antrojiprogramavimopraktika.Controller.Admin.ModalControllers.AddStudentController;
import com.example.antrojiprogramavimopraktika.Controller.Admin.ModalControllers.AssignStudentToGroupController;
import com.example.antrojiprogramavimopraktika.Entities.Student;
import com.example.antrojiprogramavimopraktika.Entities.StudentGroup;
import com.example.antrojiprogramavimopraktika.Interfaces.IStudentGroupRepository;
import com.example.antrojiprogramavimopraktika.Interfaces.IStudentRepository;
import com.example.antrojiprogramavimopraktika.Interfaces.IUserRepository;
import com.example.antrojiprogramavimopraktika.Repositories.StudentGroupRepository;
import com.example.antrojiprogramavimopraktika.Repositories.StudentRepository;
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

public final class StudentsController {

    // Students list table

    @FXML
    private TableView<Student> studentslist_table;

    @FXML
    private TableColumn<Student, Integer> studentslist_id;

    @FXML
    private TableColumn<Student, String> studentslist_firstname;

    @FXML
    private TableColumn<Student, String> studentslist_lastname;

    @FXML
    private TableColumn<Student, String> studentslist_email;

    @FXML
    private TableColumn<Student, LocalDate> studentslist_birthdate;

    @FXML
    private Button studentslist_addstudentBtn;

    @FXML
    private Button studentslist_removestudentBtn;

    // Assigned students group list table
    @FXML
    private TableView<StudentGroup> studentgrouplist_table;

    @FXML
    private TableColumn<StudentGroup, Integer> studentgrouplist_id;

    @FXML
    private TableColumn<StudentGroup, String> studentgrouplist_firstname;

    @FXML
    private TableColumn<StudentGroup, String> studentgrouplist_lastname;

    @FXML
    private TableColumn<StudentGroup, String> studentgrouplist_groupname;

    @FXML
    private Button studentgrouplist_addBtn;

    @FXML
    private Button studentgrouplist_removeBtn;

    private final IStudentRepository studentRepo = new StudentRepository();

    private final IStudentGroupRepository studentGroupRepo = new StudentGroupRepository();

    private final IUserRepository userRepo = new UserRepository();

    public void initialize() {
        // Students list
        studentslist_id.setCellValueFactory(new PropertyValueFactory<>("userID"));
        studentslist_firstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        studentslist_lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        studentslist_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        studentslist_birthdate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        // Students assigned Groups List
        studentgrouplist_id.setCellValueFactory(new PropertyValueFactory<>("studentGroupID"));

        studentgrouplist_firstname.setCellValueFactory(
                cell -> new SimpleStringProperty(cell.getValue().getStudent().getFirstName())
        );

        studentgrouplist_lastname.setCellValueFactory(
                cell -> new SimpleStringProperty(cell.getValue().getStudent().getLastName())
        );

        studentgrouplist_groupname.setCellValueFactory(
                cell -> new SimpleStringProperty(cell.getValue().getGroup().getName())
        );

        loadStudents();
        loadStudentGroups();

        studentslist_removestudentBtn.setOnAction(_ -> {
            Student selected = studentslist_table.getSelectionModel().getSelectedItem();

            if(selected == null) return;

            userRepo.removeUser(selected.getUserID());

            loadStudents();
        });

        studentslist_addstudentBtn.setOnAction(_ -> {
            try {
                SceneLoader.loadModal(
                        "/com/example/antrojiprogramavimopraktika/View/Admin/Modals/AddStudentUserModal.fxml",
                        "Studento pridėjimas",
                        modalController -> ((AddStudentController) modalController).setParent(this)
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        studentgrouplist_removeBtn.setOnAction(_ -> {
            StudentGroup selected = studentgrouplist_table.getSelectionModel().getSelectedItem();

            if(selected == null) return;

            studentGroupRepo.removeStudentFromGroup(selected.getStudentGroupID());

            loadStudentGroups();
        });


        studentgrouplist_addBtn.setOnAction(_ -> {
            try {
                SceneLoader.loadModal(
                        "/com/example/antrojiprogramavimopraktika/View/Admin/Modals/AssignStudentToGroupModal.fxml",
                        "Studento priskyrimas prie grupės",
                        modalController -> ((AssignStudentToGroupController) modalController).setParent(this)
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void loadStudents() {
        List<Student> students = studentRepo.getAllStudents();
        studentslist_table.setItems(FXCollections.observableArrayList(students));
    }

    // Loads student list with assigned student group
    public void loadStudentGroups() {
        List<StudentGroup> studentGroups = studentGroupRepo.getAllStudentGroups();
        studentgrouplist_table.setItems(FXCollections.observableArrayList(studentGroups));
    }
}
