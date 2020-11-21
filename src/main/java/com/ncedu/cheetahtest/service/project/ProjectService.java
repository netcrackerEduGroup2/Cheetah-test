package com.ncedu.cheetahtest.service.project;

import com.ncedu.cheetahtest.entity.project.Project;

import java.util.List;

public interface ProjectService {
    void createNewProject(Project newProject);
    List<Project> getAllProjects();
    List<Project> getProjectsByName(String projectName);
}
