package com.example.antrojiprogramavimopraktika.Interfaces;

import com.example.antrojiprogramavimopraktika.Entities.Student;
import com.example.antrojiprogramavimopraktika.Entities.StudentGroup;

import java.util.List;

public interface IStudentGroupRepository {

    List<StudentGroup> getAllStudentGroups();

    List<Student> getStudentsWithoutGroup();

    boolean assignStudentToGroup(int studentID, int groupID);

    void removeStudentFromGroup(int studentGroupID);
}
