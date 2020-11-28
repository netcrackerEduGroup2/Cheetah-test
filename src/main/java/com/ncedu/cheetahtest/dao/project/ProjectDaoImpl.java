package com.ncedu.cheetahtest.dao.project;

import com.ncedu.cheetahtest.entity.project.Project;
import com.ncedu.cheetahtest.entity.project.ProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ProjectDaoImpl implements ProjectDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProjectDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void createProject(ProjectDto projectDto) {
        String sqlQueryForProjectTable = ProjectSqlConsts.CREATE_PROJECT_QUERY;
        String sqlQueryForUserProjectTable = ProjectSqlConsts.CREATE_USER_PROJECT_QUERY;

        jdbcTemplate.update(
                sqlQueryForProjectTable,
                projectDto.getProject().getName(),
                projectDto.getProject().getLink(),
                "ACTIVE",
                projectDto.getProject().getCreateDate()
        );

        List<Project> project = jdbcTemplate.query(
            ProjectSqlConsts.SELECT_PROJECT_BY_NAME_QUERY,
            p -> p.setString(1, projectDto.getProject().getName()),
            new ProjectMapper()
        );

        int id = project.get(0).getId();

        for (int watcherId: projectDto.getWatcherIds()) {
            jdbcTemplate.update(
                    sqlQueryForUserProjectTable,
                    id,
                    watcherId,
                    "WATCHER"
            );
        }
    }

    @Override
    public List<Project> getAllProjects() {
        String sqlQuery = ProjectSqlConsts.SELECT_ALL_PROJECTS_QUERY;
        return jdbcTemplate.query(sqlQuery, new ProjectMapper());
    }

    @Override
    public void setArchievedStatus(int id) {
        String sqlQuery = ProjectSqlConsts.SET_ARCHIEVED_STATUS_TO_PROJECT_QUERY;
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public List<Project> getAllArchievedProjects() {
        String sqlQuery = ProjectSqlConsts.SELECT_ALL_ARCHIEVED_PROJECTS_QUERY;
        return jdbcTemplate.query(sqlQuery, new ProjectMapper());
    }

    @Override
    public Project findByProjectId(int id) {
        String sqlQuery = ProjectSqlConsts.SELECT_PROJECT_BY_ID_QUERY;
        List<Project> projects = jdbcTemplate.query(
                sqlQuery,
                preparedStatement -> preparedStatement.setInt(1, id),
                new ProjectMapper()
        );

        if (projects.size() == 1) return projects.get(0);

        return null;
    }
}
