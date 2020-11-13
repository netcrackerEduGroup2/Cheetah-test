package com.ncedu.cheetahtest.developer.dao;

import com.ncedu.cheetahtest.developer.entity.Developer;
import com.ncedu.cheetahtest.developer.entity.ResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class DeveloperDaoImpl implements DeveloperDao {

    private JdbcTemplate jdbcTemplate;

    RowMapper<Developer> ROW_MAPPER = (resultSet, i) -> {
        Developer developer = new Developer();

        developer.setId(resultSet.getInt(1));
        developer.setLogin(resultSet.getString(2));
        developer.setPass(resultSet.getString(3));
        developer.setName(resultSet.getString(4));
        developer.setType(resultSet.getString(5));
        developer.setStatus(resultSet.getString(6));
        developer.setResetPassToken(resultSet.getInt(7));

        return developer;
    };

    @Autowired
    public DeveloperDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Developer> getDevelopers() {
        String sql = "SELECT * FROM DEVELOPER";

        List<Developer> developers = jdbcTemplate.query(sql, ROW_MAPPER);

        return developers;
    }

    @Override
    public Developer findDeveloperByEmail(String email) {
        String sql = String.format("select * from DEVELOPER where login = '%s'", email);
        List<Developer> developers = jdbcTemplate.query(sql, ROW_MAPPER);

        if(developers.size() == 1 ) {
            return developers.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void saveToken(ResetToken myToken) {
        String sql = String.format("UPDATE reset_token SET token = '%s' WHERE id_developer = %d",
                myToken.getToken(),
                myToken.getDeveloper().getId());

        jdbcTemplate.update(sql);
    }
}
