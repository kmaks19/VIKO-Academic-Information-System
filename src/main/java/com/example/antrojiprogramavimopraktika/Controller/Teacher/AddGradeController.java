package com.example.antrojiprogramavimopraktika.Controller.Teacher;

import com.example.antrojiprogramavimopraktika.Entities.Session;
import com.example.antrojiprogramavimopraktika.Entities.Student;
import com.example.antrojiprogramavimopraktika.Entities.TeacherSubject;
import com.example.antrojiprogramavimopraktika.Interfaces.IGradeRepository;
import com.example.antrojiprogramavimopraktika.Interfaces.IStudentGroupRepository;
import com.example.antrojiprogramavimopraktika.Interfaces.ITeacherSubjectRepository;
import com.example.antrojiprogramavimopraktika.Repositories.GradeRepository;
import com.example.antrojiprogramavimopraktika.Repositories.StudentGroupRepository;
import com.example.antrojiprogramavimopraktika.Repositories.TeacherSubjectRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.List;

public class AddGradeController {

    public AddGradeController() {}

    private GradeController parent;

    public void setParent(GradeController parent) {
        this.parent = parent;
    }

    private final IGradeRepository gradeRepo = new GradeRepository();
    private final ITeacherSubjectRepository tsRepo = new TeacherSubjectRepository();
    private final IStudentGroupRepository sgRepo = new StudentGroupRepository();


    @FXML
    private ChoiceBox<TeacherSubject> addgrade_selectSubject;

    @FXML
    private ChoiceBox<Student> addgrade_selectStudent;

    @FXML
    private ChoiceBox<Integer> addgrade_selectGrade;

    @FXML
    private Label addgrade_errorLabel;

    @FXML
    private Button addgrade_submitBtn;

    @FXML
    private Button addgrade_cancelBtn;

    public void initialize() {

        addgrade_cancelBtn.setOnAction(_ -> close());

        loadTeacherSubjects();

        addgrade_selectSubject.getSelectionModel().selectedItemProperty().addListener((_, _, newSubject) -> {
            if(newSubject != null) {

                int teacherID = Session.getInstance().getCurrentUserInstance().getUserID();
                int subjectID = newSubject.getSubject().getID();

                loadAvailableStudents(teacherID, subjectID);
            }
        });

        addgrade_selectStudent.getSelectionModel().selectedItemProperty().addListener((_, _, newStudent) -> {
            if(newStudent != null) {
                showGradeSelection();
            }
        });

        addgrade_submitBtn.setOnAction(_ -> {
            if(save()) {
                if(parent != null) {
                    parent.loadGrades(Session.getInstance().getCurrentUserInstance().getUserID());
                }
                close();
            }
        });

    }

    private void loadTeacherSubjects() {
        List<TeacherSubject> teacherSubjects = tsRepo.getTeacherSubjects();
        addgrade_selectSubject.setItems(FXCollections.observableArrayList(teacherSubjects));

        addgrade_selectSubject.setConverter(new StringConverter<>() {
            @Override
            public String toString(TeacherSubject ts) {
                return ts == null ? "" : ts.getSubject().getName();
            }

            @Override
            public TeacherSubject fromString(String string) {
                return null;
            }
        });
    }

    private void loadAvailableStudents(int teacherID, int subjectID) {
        List<Student> students = sgRepo.getStudentsForTeacherAndSubject(teacherID, subjectID);
        addgrade_selectStudent.setItems(FXCollections.observableArrayList(students));

        addgrade_selectStudent.setConverter(new StringConverter<>() {
            @Override
            public String toString(Student student) {
                return student == null ? "" : student.getFirstName() + " " + student.getLastName();
            }

            @Override
            public Student fromString(String string) {
                return null;
            }
        });
    }

    private void showGradeSelection() {
        addgrade_selectGrade.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    private boolean save() {
        addgrade_errorLabel.setVisible(false);

        TeacherSubject selectedSubject = addgrade_selectSubject.getValue();
        Student selectedStudent = addgrade_selectStudent.getValue();
        Integer gradeValue = addgrade_selectGrade.getValue();

        if(selectedSubject == null) {
            addgrade_errorLabel.setText("Privalote pasirinkti dalyką!");
            addgrade_errorLabel.setVisible(true);
            return false;
        }

        if(selectedStudent == null) {
            addgrade_errorLabel.setText("Privalote pasirinkti studentą!");
            addgrade_errorLabel.setVisible(true);
            return false;
        }

        if(gradeValue == null) {
            addgrade_errorLabel.setText("Privalote pasirinkti pažymį!");
            addgrade_errorLabel.setVisible(true);
            return false;
        }

        boolean success = gradeRepo.addGrade(
                selectedStudent.getUserID(),
                selectedSubject.getSubject().getID(),
                gradeValue,
                LocalDate.now()
        );

        if(!success) {
            addgrade_errorLabel.setText("Nepavyko įrašyti pažymio!");
            addgrade_errorLabel.setVisible(true);
            return false;
        }
        return true;
    }

    private void close() {
        ((Stage) addgrade_cancelBtn.getScene().getWindow()).close();
    }
}
