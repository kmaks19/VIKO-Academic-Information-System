package com.example.antrojiprogramavimopraktika.Interfaces;

import com.example.antrojiprogramavimopraktika.Entities.GroupSubject;
import com.example.antrojiprogramavimopraktika.Entities.Subject;

import java.util.List;

public interface IGroupSubjectRepository {

    List<Subject> getUnassignedSubjectsForGroup(int groupID);

    List<GroupSubject> getGroupSubjects();

    boolean addGroupSubject(int groupID, int subjectID);

    void removeGroupSubject(int groupSubjectID);
}
