package com.ncedu.cheetahtest.dao.project;

import com.ncedu.cheetahtest.entity.testcase.TestCase;
import com.ncedu.cheetahtest.entity.project.Project;
import com.ncedu.cheetahtest.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ProjectDaoImpl implements ProjectDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProjectDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createProject(Project project) {
        String sqlQuery = "INSERT INTO project (id, name, link, status, create_date, owner_id)" +
                          "VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sqlQuery,
                project.getId(),
                project.getName(),
                project.getLink(),
                project.getStatus(),
                project.getCreateDate(),
                project.getIdOwner()
        );
    }

    @Override
    public List<Project> getAllProjects() {
        String sqlSelectQuery = "SELECT id,name,link, status, create_date, owner_id FROM project";
        return jdbcTemplate.query(sqlSelectQuery, new ProjectMapper());
    }

    // TODO: 21.11.2020  
    @Override
    public List<Project> findByProjectName(String projectName) {
        String sqlSelectByName = "SELECT id, name, link, status, create_date, owner_id FROM project" +
                                 "WHERE name = ?";
        return null;
    }

    @Override
    public List<Project> findByOwner(String ownerName) {
        String sqlSelectByOwner = "SELECT id, name, link, status, create_date, owner_id " +
                                  "FROM project p INNER JOIN users u ON p.owner_id = u.id" +
                                  "WHERE u.name = ?";
        return null;
    }

    @Override
    public List<Project> findByCreationDate(Date date) {
        return null;
    }

    @Override
    public void addWatchers(List<User> watchers) {

    }

    @Override
    public void addTestCases(List<TestCase> testCases) {

    }
}
