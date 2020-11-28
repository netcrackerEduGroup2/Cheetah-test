package com.ncedu.cheetahtest.dao.genericdao;

import com.ncedu.cheetahtest.exception.general.EntityNotFoundException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractDao<T> {

    protected final RowMapper<T> rowMapper;

    protected final JdbcTemplate jdbcTemplate;

    protected final Map<StableQuery, String> constsMap;


    protected AbstractDao(RowMapper<T> rowMapper,
                          JdbcTemplate jdbcTemplate,
                          Consts consts) {

        this.constsMap = consts.getConstMap();
        this.rowMapper = rowMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<T> getActivePaginated(int offset, int size) {
        return jdbcTemplate.query(
                constsMap.get(StableQuery.GET_ACTIVE_PAGINATED),
                preparedStatement -> {
                    preparedStatement.setInt(1, size);
                    preparedStatement.setInt(2, offset);
                },
                rowMapper
        );
    }

    public List<T> getAllPaginated(int offset, int size) {

        return jdbcTemplate.query(
                constsMap.get(StableQuery.GET_ALL_PAGINATED),
                preparedStatement -> {
                    preparedStatement.setInt(1, size);
                    preparedStatement.setInt(2, offset);
                },
                rowMapper
        );
    }

    public int getAmountActiveElements() {
        Integer amountOfTestCases = DataAccessUtils.singleResult(
                jdbcTemplate.queryForList(
                        constsMap.get(StableQuery.AMOUNT_ACTIVE),
                        Integer.class
                )
        );

        return Objects.requireNonNullElse(amountOfTestCases, 0);
    }

    public int getAmountAllElements() {
        Integer amountOfTestCases = DataAccessUtils.singleResult(
                jdbcTemplate.queryForList(
                        constsMap.get(StableQuery.AMOUNT_ALL),
                        Integer.class
                )
        );

        return Objects.requireNonNullElse(amountOfTestCases, 0);
    }

    public T findById(int id) {

        List<T> list = jdbcTemplate.query(
                constsMap.get(StableQuery.FIND_BY_ID),
                preparedStatement -> preparedStatement.setInt(1, id),
                rowMapper
        );

        if (list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

    public void deactivate(int id) {
        int result = jdbcTemplate.update(
                constsMap.get(StableQuery.DEACTIVATE),
                id);
        if (result != 1) {
            throw new EntityNotFoundException("Exception in " + getClass().getName());
        }
    }

    public int getSearchedTotalElements(String title) {
        return getSingleIntElement("%" + title + "%",
                constsMap.get(StableQuery.AMOUNT_ACTIVE_SEARCHED));
    }

    public int getSearchedAllTotalElements(String title) {
        return getSingleIntElement("%" + title + "%",
                constsMap.get(StableQuery.AMOUNT_ALL_SEARCHED));
    }

    public List<T> findByTitlePaginated(int offset, int size, String title) {
        return jdbcTemplate.query(
                constsMap.get(StableQuery.ACTIVE_SEARCHED),
                preparedStatement -> {
                    preparedStatement.setString(1, "%" + title + "%");
                    preparedStatement.setInt(2, size);
                    preparedStatement.setInt(3, offset);
                },
                rowMapper
        );
    }

    public List<T> findAllByTitlePaginated(int offset, int size, String title) {
        return jdbcTemplate.query(
                constsMap.get(StableQuery.ALL_SEARCHED),
                preparedStatement -> {
                    preparedStatement.setString(1, "%" + title + "%");
                    preparedStatement.setInt(2, size);
                    preparedStatement.setInt(3, offset);
                },
                rowMapper
        );
    }

    public int getSingleIntElement(String title, String getAmountOfAllSearchedTestCases) {
        List<Integer> amountOfTestCases = jdbcTemplate.query(
                getAmountOfAllSearchedTestCases,
                preparedStatement ->
                        preparedStatement.setString(1, title),
                (resultSet, i) -> resultSet.getInt(1)
        );

        if (amountOfTestCases.size() == 1) {
            return amountOfTestCases.get(0);
        }

        return 0;
    }

}