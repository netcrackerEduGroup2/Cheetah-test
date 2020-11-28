package com.ncedu.cheetahtest.service.project;

import com.ncedu.cheetahtest.entity.project.Project;
import com.ncedu.cheetahtest.entity.project.ProjectDto;

import java.util.List;

public interface ProjectService {
    void createNewProject(ProjectDto newProject);
    List<Project> getAllProjects();
    List<Project> getAllArchievedProjects();
    void setArchievedStatus(int id);
    Project getProjectById(int id);
    List<Project> getProjectByTitle(String filter);
}
