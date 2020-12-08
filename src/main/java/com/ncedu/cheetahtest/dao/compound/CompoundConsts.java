package com.ncedu.cheetahtest.dao.compound;

public class CompoundConsts {

    public static final String CREATE_COMPOUND = "INSERT INTO compound (title, description) VALUES (?,?)";
    public static final String FIND_COMPOUND_BY_ID = "SELECT id,title,description FROM compound WHERE id = ?";
    public static final String SELECT_COMPOUND_BY_TITLE_LIKE = "SELECT compound.id, compound.title, compound.description " +
            "FROM compound WHERE title LIKE CONCAT ('%',?,'%') ORDER BY title limit ? offset ?";
    public static final String SELECT_COMPOUND_BY_TITLE_LIKE_WITHOUT_PAGINATION = "SELECT compound.id, compound.title, compound.description " +
            "FROM compound WHERE title LIKE CONCAT ('%',?,'%')";
    public static final String EDIT_COMPOUND = "UPDATE compound SET title = ?, description =? WHERE id = ?";
    public static final String REMOVE_COMPOUND_BY_ID = "DELETE FROM compound WHERE id = ?";
    public static final String FIND_BY_TITLE = "SELECT compound.id, compound.title,compound.description " +
            "FROM compound WHERE title = ?";
    public static final String GET_TOTAL_COMP_BY_TITLE = "SELECT COUNT(*) FROM compound WHERE title LIKE CONCAT('%',?,'%')";
    public static final String GET_COMP_ID_BY_ACTION_ID = "SELECT id, title, description FROM compound WHERE id IN (SELECT comp_id FROM comp_act_prior WHERE action_id = ?) LIMIT 1";

}
