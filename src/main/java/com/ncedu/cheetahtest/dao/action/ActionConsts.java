package com.ncedu.cheetahtest.dao.action;

public class ActionConsts {

    public static final String SELECT_ACTIONS_BY_TITLE_LIKE =
            "SELECT action.id,action.title,action.type,description " +
                    "FROM action " +
                    "WHERE title LIKE CONCAT('%',?,'%') ORDER BY title limit ? offset ?";


}
