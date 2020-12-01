package com.ncedu.cheetahtest.dao.project;

import com.ncedu.cheetahtest.entity.project.Project;
import com.ncedu.cheetahtest.entity.project.ProjectDto;

import java.util.List;

public interface ProjectDao {

    void createProject(ProjectDto project);

    void setArchievedStatus(int id);

    List<Project> getAllArchievedProjects();

    Project findByProjectId(int id);

    int getAmountActiveElements();

    List<Project> getActivePaginated(int page, int size);

    int getSearchedActiveTotalElements(String title);

    List<Project> findActiveByTitlePaginated(int page, int size, String title);

    void updateProjectById(int id, Project project);
}
