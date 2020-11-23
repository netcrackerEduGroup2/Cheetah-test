package com.ncedu.cheetahtest.dao.project;

import com.ncedu.cheetahtest.entity.project.Project;
import java.sql.Timestamp;
import java.util.List;

public interface ProjectDao {

    void createProject(Project project);

    List<Project> getAllProjects();

    List<Project> findByProjectName(String projectName);

    List<Project> findByOwner(String ownerName);

    List<Project> findByCreationDate(Timestamp date);
}
