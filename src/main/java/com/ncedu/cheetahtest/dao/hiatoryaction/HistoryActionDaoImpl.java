package com.ncedu.cheetahtest.dao.hiatoryaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import static com.ncedu.cheetahtest.dao.hiatoryaction.HistoryActionConstant.ADD_HISTORY_ACTION;

@Repository
public class HistoryActionDaoImpl implements HistoryActionDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HistoryActionDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public void addAction(String result, String screenshotURL, int generalOrder, int idHistoryTestCase) {
        jdbcTemplate.update(
                ADD_HISTORY_ACTION,
                preparedStatement -> {
                    preparedStatement.setString(1, result);
                    preparedStatement.setString(2, screenshotURL);
                    preparedStatement.setInt(3, generalOrder);
                    preparedStatement.setInt(4, idHistoryTestCase);
                });
    }
}
