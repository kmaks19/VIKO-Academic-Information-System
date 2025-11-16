package com.example.antrojiprogramavimopraktika.Interfaces;

import com.example.antrojiprogramavimopraktika.Entities.Group;

import java.util.List;

public interface IGroupRepository {
    List<Group> getGroups();

    void deleteGroup(int groupID);

    boolean checkIfUnique(String groupName);

    boolean addGroup(String groupName);
}