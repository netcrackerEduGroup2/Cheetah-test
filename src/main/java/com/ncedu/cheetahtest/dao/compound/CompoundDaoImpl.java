package com.ncedu.cheetahtest.dao.compound;

import com.ncedu.cheetahtest.entity.compound.Compound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CompoundDaoImpl implements CompoundDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CompoundDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Compound createCompound(Compound compound) {
        String sql = "INSERT INTO compound (title, description) VALUES (?,?)";
        jdbcTemplate.update(
                sql,
                compound.getTitle(),
                compound.getDescription()
        );
        return this.findByTitle(compound.getTitle());
    }

    private Compound findCompoundById(int id) {
        String sql = "SELECT id,title,description FROM compound WHERE id = ?";
       List<Compound> compounds = jdbcTemplate.query(
               sql,
               preparedStatement -> preparedStatement.setInt(1,id),
               new CompoundRowMapper());
       if (compounds.size() == 1){
           return compounds.get(0);
       }
       else{
           return null;
       }

    }

    @Override
    public List<Compound> selectCompoundsByTitleLike(String title, int limit, int offset) {
        String sql = "SELECT compound.id, compound.title, compound.description " +
                "FROM compound " +
                "WHERE title LIKE CONCAT ('%',?,'%') ORDER BY title limit ? offset ?";
        return jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1,title);
                    preparedStatement.setInt(2, limit);
                    preparedStatement.setInt(3,offset);
                },
                new CompoundRowMapper()
                );

    }

    @Override
    public Compound editCompound(Compound compound,int id) {
        String sql = "UPDATE compound SET title = ?, description =? WHERE id = ?";
        jdbcTemplate.update(
                sql,
                compound.getTitle(),
                compound.getDescription(),
                id
        );

        return findCompoundById(id);
    }

    @Override
    public void removeCompoundById(int id) {
        String sql = "DELETE FROM compound WHERE id = ?";
        jdbcTemplate.update(
                sql,
                id
        );
    }

    @Override
    public Compound findByTitle(String title) {
        String sql = "SELECT compound.id, compound.title,compound.description " +
                "FROM compound WHERE title = ?";
        List<Compound>compounds = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1,title),
                new CompoundRowMapper()
        );
        if(compounds.size() ==1){
            return compounds.get(0);
        }
        else {
            return null;
        }
    }

    @Override
    public int getTotalCompByTitle(String title) {
        String sql = "SELECT COUNT(*) FROM compound WHERE title LIKE CONCAT('%',?,'%')";
        List<Integer> count = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1,title),
                new CountCompoundRowMapper()
        );
        if (count.size() == 1){
            return count.get(0);
        }
        else{
            return 0;
        }
    }
}
