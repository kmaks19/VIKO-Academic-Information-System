package com.example.antrojiprogramavimopraktika.Interfaces;

import com.example.antrojiprogramavimopraktika.Entities.Grade;

import java.time.LocalDate;
import java.util.List;

public interface IGradeRepository {

    List<Grade> getGradeListForTeacher(int teacherID);

    boolean addGrade(int studentID, int subjectID, int grade, LocalDate gradeDate);

    boolean updateGrade(int gradeID, int newGrade);

    List<Grade> getGradeListForStudent(int studentID);
}
