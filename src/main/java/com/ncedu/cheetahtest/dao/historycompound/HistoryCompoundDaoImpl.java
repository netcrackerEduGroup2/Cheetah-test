package com.ncedu.cheetahtest.dao.historycompound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import static com.ncedu.cheetahtest.dao.historycompound.HistoryCompoundConstant.ADD_HISTORY_COMPOUND;

@Repository
public class HistoryCompoundDaoImpl implements HistoryCompoundDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HistoryCompoundDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void addCompound(String actionType, String element,
                            String argument, int generalOrder,int idHistoryTestCase) {
        jdbcTemplate.update(
                ADD_HISTORY_COMPOUND,
                preparedStatement -> {
                    preparedStatement.setString(1, actionType);
                    preparedStatement.setString(2, element);
                    preparedStatement.setString(3, argument);
                    preparedStatement.setInt(4, generalOrder);
                    preparedStatement.setInt(5, idHistoryTestCase);
                });
    }
}
