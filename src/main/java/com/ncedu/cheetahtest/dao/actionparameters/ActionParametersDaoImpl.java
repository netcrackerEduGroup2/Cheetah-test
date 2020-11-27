package com.ncedu.cheetahtest.dao.actionparameters;

import com.ncedu.cheetahtest.entity.actionparameters.ActionParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActionParametersDaoImpl implements ActionParametersDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ActionParametersDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ActionParameter createActionParam(ActionParameter actionParameter) {
        String sql = "INSERT INTO action_parameters (id, parameters_id) VALUES (?,?)";
        jdbcTemplate.update(
                sql,
                actionParameter.getId(),
                actionParameter.getParametersId()
        );
        return this.findById(actionParameter.getId());
    }

    @Override
    public ActionParameter findById(int id) {
        String sql = "SELECT id,parameters_id FROM action_parameters WHERE id = ?";
        List<ActionParameter> actionParameters = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1,id),
                new ActionParametersRowMapper()
        );
        if (actionParameters.size() == 1 ){
            return actionParameters.get(0);
        }
        else {
            return null;
        }
    }

    @Override
    public void deleteByIdActParam(int idActParam) {

    }
}
