package com.ncedu.cheetahtest.dao.dataset;

import com.ncedu.cheetahtest.entity.dataset.DataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class DataSetDaoImpl implements DataSetDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DataSetDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public DataSet findById(int id) {
        String sql = "SELECT id,title,description,id_test_case " +
                "FROM data_set WHERE id = ?";
        List<DataSet> dataSets = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setInt(1, id),
                new DataSetRowMapper()
        );
        if (dataSets.size() == 1) {
            return dataSets.get(0);
        } else return null;
    }

    @Override
    public List<DataSet> findByTitleLike(String title, int idTestCase, int limit, int offset) {
        String sql = "SELECT id,title,description,id_test_case " +
                "FROM data_set WHERE title LIKE CONCAT('%',?,'%') AND id_test_case=? " +
                "ORDER BY title limit ? offset ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, idTestCase);
                    preparedStatement.setInt(3, limit);
                    preparedStatement.setInt(4, offset);
                },
                new DataSetRowMapper());

    }

    @Override
    public DataSet createDataSet(DataSet dataSet) {
        String sql = "INSERT INTO data_set(title, description, id_test_case) VALUES (?,?,?)";
        jdbcTemplate.update(
                sql,
                dataSet.getTitle(),
                dataSet.getDescription(),
                dataSet.getIdTestCase()
        );
        return this.findByTitle(dataSet.getTitle());


    }

    @Override
    public DataSet editDataSet(DataSet dataSet, int id) {
        String sql = "UPDATE data_set SET title = ? , description = ? ,id_test_case = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(
                sql,
                dataSet.getTitle(),
                dataSet.getDescription(),
                dataSet.getIdTestCase(),
                id
        );
        return findByTitle(dataSet.getTitle());
    }

    @Override
    public void deleteDataSet(int id) {
        String sql = "DELETE FROM data_set WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public int getTotalElements(int idTestCase) {
        String sql = "SELECT count(*) FROM data_set WHERE id_test_case = ?";
        List<Integer> count = jdbcTemplate.query(sql,
                preparedStatement -> preparedStatement.setInt(1,idTestCase),
                new CountDataSetRowMapper());
        if (count.size() == 1) return count.get(0);
        else return 0;
    }

    @Override
    public DataSet findByTitle(String title) {
        String sql = "SELECT id,title,description,id_test_case FROM data_set " +
                "WHERE title = ?";
        List<DataSet> dataSets = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, title),
                new DataSetRowMapper()
        );
        if (dataSets.size() == 1) {
            return dataSets.get(0);
        } else return null;
    }
}
