package com.ncedu.cheetahtest.dao.testscenario;

import com.ncedu.cheetahtest.dao.genericdao.AbstractDaoImpl;
import com.ncedu.cheetahtest.dao.genericdao.Consts;
import com.ncedu.cheetahtest.entity.testscenario.TestScenario;
import com.ncedu.cheetahtest.exception.general.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ncedu.cheetahtest.dao.testscenario.TestScenarioConsts.*;

@Repository
public class TestScenarioDaoImpl extends AbstractDaoImpl<TestScenario> implements TestScenarioDao {

    private static final String[] rows = {"id", "title", "description", "status", "test_case_id"};
    private final TestScenarioMapper testScenarioMapper;
    private static final String TABLE_NAME = "test_scenario";

    @Autowired
    public TestScenarioDaoImpl(JdbcTemplate jdbcTemplate,
                               TestScenarioMapper testScenarioMapper,
                               ApplicationContext applicationContext) {
        super(testScenarioMapper, jdbcTemplate, applicationContext.getBean(Consts.class, rows, TABLE_NAME));
        this.testScenarioMapper = testScenarioMapper;
    }

    @Override
    public List<TestScenario> findByTitleLikeAndIdTestCase(String title, int idTestCase, int limit, int offset) {
        return jdbcTemplate.query(
                FIND_BY_TITLE_AND_ID_TEST_CASE_LIKE,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, idTestCase);
                    preparedStatement.setInt(3, limit);
                    preparedStatement.setInt(4, offset);
                },
                testScenarioMapper);
    }

    @Override
    public List<TestScenario> findByTitleLike(String title, int limit, int offset) {
        return jdbcTemplate.query(
                FIND_BY_TITLE_LIKE,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, limit);
                    preparedStatement.setInt(3, offset);
                },
                testScenarioMapper);
    }

    @Override
    public List<TestScenario> findTestScenariosFromTestCase(int idTestCase, int limit, int offset) {
        return jdbcTemplate.query(
                FIND_SCENARIOS_FROM_TEST_CASE,
                preparedStatement -> {
                    preparedStatement.setInt(1, idTestCase);
                    preparedStatement.setInt(2, limit);
                    preparedStatement.setInt(3, offset);
                },
                testScenarioMapper
        );
    }

    @Override
    public TestScenario createTestScenario(TestScenario testScenario) {
        jdbcTemplate.update(
                CREATE_TEST_SCENARIO,
                testScenario.getTitle(),
                testScenario.getDescription(),
                testScenario.getStatus().toString(),
                testScenario.getIdTestCase()
        );
        return this.findByTitle(testScenario.getTitle());
    }

    @Override
    public TestScenario editTestScenario(TestScenario testScenario, int id) {
        jdbcTemplate.update(
                EDIT_TEST_SCENARIO,
                testScenario.getTitle(),
                testScenario.getDescription(),
                testScenario.getStatus().toString(),
                testScenario.getIdTestCase(),
                id
        );
        return findByTitle(testScenario.getTitle());
    }

    @Override
    public void deleteTestScenario(int id) {
        jdbcTemplate.update(DELETE_TEST_SCENARIO, id);
    }

    @Override
    public int getTotalElementsByTitleAndIdTestCase(int idTestCase, String title) {
        List<Integer> count = jdbcTemplate.query(
                GET_TOTAL_ELEMENTS_FROM_SEARCH_BY_ID_TEST_CASE,
                preparedStatement -> {
                    preparedStatement.setInt(1, idTestCase);
                    preparedStatement.setString(2, title);
                },
                new CountTestScenarioRowMapper());
        if (count.size() == 1) return count.get(0);
        else return 0;
    }

    @Override
    public int getTotalElementsByTitle(String title) {
        List<Integer> count = jdbcTemplate.query(
                GET_TOTAL_ELEMENTS_FROM_SEARCH,
                preparedStatement -> preparedStatement.setString(1, title),
                new CountTestScenarioRowMapper());
        if (count.size() == 1) return count.get(0);
        else return 0;
    }

    @Override
    public int getTestScenariosFromTestCaseAmount(int idTestCase) {
        List<Integer> count = jdbcTemplate.query(
                GET_SCENARIOS_FROM_TEST_CASE_AMOUNT,
                preparedStatement -> preparedStatement.setInt(1, idTestCase),
                new CountTestScenarioRowMapper());
        if (count.size() == 1) return count.get(0);
        else return 0;
    }

    @Override
    public TestScenario findByTitle(String title) {
        List<TestScenario> testScenarios = jdbcTemplate.query(
                FIND_BY_TITLE,
                preparedStatement -> preparedStatement.setString(1, title),
                testScenarioMapper
        );
        if (testScenarios.size() == 1) {
            return testScenarios.get(0);
        } else return null;
    }

    @Override
    public void deactivate(int id) {
        int result = jdbcTemplate.update(DEACTIVATE_TEST_SCENARIO, id);
        if (result != 1) {
            throw new EntityNotFoundException("Exception in " + getClass().getName());
        }
    }

    @Override
    public TestScenario findTestScenarioByTitleExceptCurrent(String title, int id) {
        List<TestScenario> testScenarios = jdbcTemplate.query(
                FIND_TEST_SCENARIO_BY_TITLE_EXCEPT_CURRENT,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, id);
                },
                testScenarioMapper
        );

        if (testScenarios.size() == 1) {
            return testScenarios.get(0);
        }

        return null;
    }

}
