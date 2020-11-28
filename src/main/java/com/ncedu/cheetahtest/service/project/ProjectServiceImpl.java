package com.ncedu.cheetahtest.service.project;

import com.ncedu.cheetahtest.dao.project.ProjectDao;
import com.ncedu.cheetahtest.entity.project.Project;
import com.ncedu.cheetahtest.entity.project.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectDao projectDao;

    @Autowired
    public ProjectServiceImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public void createNewProject(ProjectDto newProjectDto) {
        projectDao.createProject(newProjectDto);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectDao.getAllProjects();
    }

    @Override
    public List<Project> getAllArchievedProjects() {
        return projectDao.getAllArchievedProjects();
    }

    @Override
    public void setArchievedStatus(int id) {
        projectDao.setArchievedStatus(id);
    }

    @Override
    public Project getProjectById(int id) {
        return projectDao.findByProjectId(id);
    }

}
