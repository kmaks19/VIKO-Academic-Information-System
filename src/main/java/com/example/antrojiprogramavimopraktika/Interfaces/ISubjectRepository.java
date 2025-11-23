package com.example.antrojiprogramavimopraktika.Interfaces;

import com.example.antrojiprogramavimopraktika.Entities.Subject;

import java.util.List;

public interface ISubjectRepository {

    List<Subject> getAllUnassignedSubjects();

    List<Subject> getAllSubjects();

    boolean addSubject(String subjectName);

    boolean removeSubject(int subjectID);

    boolean getSubjectByName(String subjectName);

}