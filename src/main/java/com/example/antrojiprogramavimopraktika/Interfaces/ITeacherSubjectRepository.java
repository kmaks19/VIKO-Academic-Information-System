package com.example.antrojiprogramavimopraktika.Interfaces;
import com.example.antrojiprogramavimopraktika.Entities.TeacherSubject;

import java.util.List;

public interface ITeacherSubjectRepository {
    List<TeacherSubject> getTeacherSubjects();
    boolean addTeacherSubject(int teacherID, int subjectID);
    void removeTeacherSubject(int tsSubjectID);
}
