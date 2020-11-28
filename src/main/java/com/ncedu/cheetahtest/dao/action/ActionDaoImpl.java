package com.ncedu.cheetahtest.dao.action;


import com.ncedu.cheetahtest.entity.action.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

import static com.ncedu.cheetahtest.dao.action.ActionConsts.SELECT_ACTIONS_BY_TITLE_LIKE;

@Repository
public class ActionDaoImpl implements ActionDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ActionDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Action> selectActionsByTitleLike(String title,int limit,int offset) {
        return jdbcTemplate.query(
                SELECT_ACTIONS_BY_TITLE_LIKE,
                preparedStatement -> {
                    preparedStatement.setString(1,title);
                    preparedStatement.setInt(2,limit);
                    preparedStatement.setInt(3,offset);
                },
                new ActionRowMapper());
    }

    @Override
    public int getTotalElements(String title) {
       String sql = "SELECT count(*) FROM action WHERE title LIKE CONCAT('%',?,'%')";
       List<Integer> totalElements= jdbcTemplate.query(
               sql,
               preparedStatement -> preparedStatement.setString(1,title),
               new CountActionRowMapper()
       );
       if (totalElements.size() == 1){
           return totalElements.get(0);
       }
       else return 0;


    }

    @Override
    public List<Action> getActionsInCompound(int idCompound,int limit,int offset) {
        String sql = "SELECT action.id, action.title, action.type, action.description " +
                "FROM action INNER JOIN comp_act_prior ON action.id = comp_act_prior.action_id " +
                "INNER JOIN compound c ON comp_act_prior.comp_id = c.id " +
                "WHERE c.id = ? ORDER BY comp_act_prior.priority " +
                "LIMIT ? OFFSET ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setInt(1,idCompound);
                    preparedStatement.setInt(2,limit);
                    preparedStatement.setInt(3,offset);
                },
                new ActionRowMapper()
        );
    }

    @Override
    public int getTotalActionsInComp(int idCompound) {
        String sql = "SELECT COUNT(*)" +
                "FROM action INNER JOIN comp_act_prior ON action.id = comp_act_prior.action_id " +
                "INNER JOIN compound c ON comp_act_prior.comp_id = c.id " +
                "WHERE c.id = ? ";
        List<Integer> counts = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, idCompound),
                new CountActionRowMapper()
        );
        if(counts.size() == 1){
            return counts.get(0);
        }
        else{
            return 0;
        }
    }

    @Override
    public Action getActionByTitle(String title) {
        String sql = "SELECT id, title, type,description FROM action WHERE title = ?";
        List<Action> actions = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1,title),
                new ActionRowMapper()
        );
        if(actions.size() == 1){
            return actions.get(0);
        }
        else return null;
    }


    @Override
    public Action getActionById(int id) {
        String sql = "SELECT id, title, type,description FROM action WHERE id = ?";
        List<Action> actions = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1,id),
                new ActionRowMapper()
        );
        if(actions.size() == 1){
            return actions.get(0);
        }
        else return null;
    }

    @Override
    public Action editActionDesc(String description, int id) {
        String sql = "UPDATE action SET description = ? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                preparedStatement ->{
                    preparedStatement.setString(1,description);
                    preparedStatement.setInt(2,id);
                } );
        return this.getActionById(id);
    }

    @Override
    public List<Action> selectAllActionsByTitleLike(String title) {
       String sql =  "SELECT action.id,action.title,action.type,description " +
                "FROM action " +
                "WHERE title LIKE CONCAT('%',?,'%') LIMIT 10";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1,title),
                new ActionRowMapper());
    }

    @Override
    public List<Action> getAllActionsInComp(int idComp) {
        String sql = "SELECT action.id, action.title, action.type, action.description " +
                "FROM action INNER JOIN comp_act_prior ON action.id = comp_act_prior.action_id " +
                "INNER JOIN compound c ON comp_act_prior.comp_id = c.id " +
                "WHERE c.id = ? ORDER BY comp_act_prior.priority ";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1,idComp),
                new ActionRowMapper()
        );
    }
}
