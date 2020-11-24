package com.ncedu.cheetahtest.dao.project;

public final class ProjectSqlConsts {
    public static final String CREATE_PROJECT_QUERY      = "INSERT INTO project (name, link, status, create_date, owner_id) " +
                                                           "VALUES (?, ?, ?::project_status, ?, ?)";
    public static final String DELETE_PROJECT_QUERY      = "DELETE FROM project " +
                                                           "WHERE id = ?";
    public static final String SELECT_ALL_PROJECTS_QUERY = "SELECT id, name, link, status, create_date, owner_id " +
                                                           "FROM project";
    public static final String SELECT_PROJECT_BY_ID_QUERY = "SELECT id, name, link, status, create_date, owner_id " +
                                                            "FROM project " +
                                                            "WHERE id = ?";
    public static final String SELECT_PROJECT_BY_OWNER_NAME_QUERY = "SELECT id, name, link, status, create_date, owner_id " +
                                                                    "FROM project p INNER JOIN users u ON p.owner_id = u.id " +
                                                                    "WHERE u.name = ?";
    public static final String SELECT_PROJECTS_BY_CREATION_DATE = "SELECT id, name, link, status, create_date, owner_id " +
                                                                  "FROM project " +
                                                                  "WHERE create_date = ?";
}
