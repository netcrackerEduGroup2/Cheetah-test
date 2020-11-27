package com.ncedu.cheetahtest.dao.compactprior;

import com.ncedu.cheetahtest.entity.compactprior.CompActPrior;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class CompActPriorDaoImpl implements CompActPriorDao{
    private final JdbcTemplate jdbcTemplate;
    @Autowired

    public CompActPriorDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createCompActPrior(CompActPrior compActPrior) {
        String sql = "INSERT INTO comp_act_prior (comp_id,action_id,priority) VALUES (?,?,?)";
        jdbcTemplate.update(
                sql,
                compActPrior.getCompId(),
                compActPrior.getActId(),
                compActPrior.getPriority()
                );
    }

    @Override
    public void deleteByIdCompound(int idCopmound) {
        String sql = "DELETE FROM comp_act_prior WHERE comp_id = ?";
        jdbcTemplate.update(sql,
                idCopmound);
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM comp_act_prior WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
