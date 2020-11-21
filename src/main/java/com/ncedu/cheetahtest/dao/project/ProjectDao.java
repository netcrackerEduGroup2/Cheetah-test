package com.ncedu.cheetahtest.dao.project;

import com.ncedu.cheetahtest.entity.testcase.TestCase;
import com.ncedu.cheetahtest.entity.project.Project;
import com.ncedu.cheetahtest.entity.user.User;

import java.util.Date;
import java.util.List;

public interface ProjectDao {

    void createProject(Project project);

    List<Project> getAllProjects();

    List<Project> findByProjectName(String projectName);

    List<Project> findByOwner(String ownerName);

    List<Project> findByCreationDate(Date date);

    void addWatchers(List<User> watchers);

    void addTestCases(List<TestCase> testCases);

}
