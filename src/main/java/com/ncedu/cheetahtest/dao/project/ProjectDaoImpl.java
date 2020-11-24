package com.ncedu.cheetahtest.dao.project;

import com.ncedu.cheetahtest.entity.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ProjectDaoImpl implements ProjectDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProjectDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void createProject(Project project) {
        String sqlQuery = ProjectSqlConsts.CREATE_PROJECT_QUERY;
        jdbcTemplate.update(
                sqlQuery,
                project.getName(),
                project.getLink(),
                project.getStatus(),
                Timestamp.valueOf(LocalDateTime.now()),
                project.getOwnerId()
        );
    }

    @Override
    public List<Project> getAllProjects() {
        String sqlQuery = ProjectSqlConsts.SELECT_ALL_PROJECTS_QUERY;
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

    @Override
    public List<Project> findByOwner(String ownerName) {
        String sqlQuery = ProjectSqlConsts.SELECT_PROJECT_BY_OWNER_NAME_QUERY;
        return jdbcTemplate.query(
                sqlQuery,
                preparedStatement -> preparedStatement.setString(1, ownerName),
                new ProjectMapper()
        );
    }

    @Override
    public List<Project> findByCreationDate(Timestamp date) {
        String sqlQuery = ProjectSqlConsts.SELECT_PROJECTS_BY_CREATION_DATE;
        return jdbcTemplate.query(
                sqlQuery,
                preparedStatement -> {
                        preparedStatement.setTimestamp(1, date);
                },
                new ProjectMapper()
        );
    }
}
