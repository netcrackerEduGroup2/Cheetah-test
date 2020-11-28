package com.ncedu.cheetahtest.dao.testcase;

import com.ncedu.cheetahtest.entity.testcase.TestCase;
import com.ncedu.cheetahtest.exception.testcase.TestCaseNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.ncedu.cheetahtest.dao.testcase.TestCaseConsts.*;

@Repository
@RequiredArgsConstructor
public class TestCaseDaoImpl implements TestCaseDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<TestCase> getTestCases(int offset, int size) {

        return jdbcTemplate.query(
                GET_ACTIVE_TEST_CASES_PAGINATED,
                preparedStatement -> {
                    preparedStatement.setInt(1, size);
                    preparedStatement.setInt(2, offset);
                },
                new TestCaseMapper()
        );
    }

    @Override
    public int getTotalElements() {
        Integer amountOfTestCases = DataAccessUtils.singleResult(
                jdbcTemplate.queryForList(
                        GET_AMOUNT_OF_ACTIVE_TEST_CASES,
                        Integer.class
                )
        );

        return Objects.requireNonNullElse(amountOfTestCases, 0);
    }

    @Override
    public List<TestCase> getAllTestCases(int offset, int size) {

        return jdbcTemplate.query(
                GET_ALL_TEST_CASES_PAGINATED,
                preparedStatement -> {
                    preparedStatement.setInt(1, size);
                    preparedStatement.setInt(2, offset);
                },
                new TestCaseMapper()
        );
    }

    @Override
    public int getAllTotalElements() {
        Integer amountOfTestCases = DataAccessUtils.singleResult(
                jdbcTemplate.queryForList(
                        GET_AMOUNT_OF_ALL_TEST_CASES,
                        Integer.class
                )
        );

        return Objects.requireNonNullElse(amountOfTestCases, 0);
    }

    @Override
    public void save(TestCase testCase) {
        int result = jdbcTemplate.update(UPDATE_TEST_CASE_SQL,
                testCase.getTitle(),
                testCase.getProjectId(),
                testCase.getStatus().toString(),
                testCase.getResult().toString(),
                testCase.getId());

        if (result != 1) {
            throw new TestCaseNotFoundException();
        }
    }

    @Override
    public TestCase findTestCaseById(int id) {

        List<TestCase> testCases = jdbcTemplate.query(
                FIND_TEST_CASE_BY_ID,
                preparedStatement -> preparedStatement.setInt(1, id),
                new TestCaseMapper()
        );

        if (testCases.size() == 1) {
            return testCases.get(0);
        }

        return null;
    }

    @Override
    public TestCase findTestCaseByTitleExceptCurrent(String title, int id) {
        List<TestCase> testCases = jdbcTemplate.query(
                FIND_TEST_CASE_BY_TITLE_EXCEPT_CURRENT,
                preparedStatement -> {
                    preparedStatement.setString(1, title);
                    preparedStatement.setInt(2, id);
                },
                new TestCaseMapper()
        );

        if (testCases.size() == 1) {
            return testCases.get(0);
        }

        return null;
    }

    @Override
    public void deactivateTestCase(int id) {
        int result = jdbcTemplate.update(DEACTIVATE_TEST_CASE_SQL, id);
        if (result != 1) {
            throw new TestCaseNotFoundException();
        }
    }

    @Override
    public List<TestCase> findTestCasesByTitlePaginated(int offset, int size, String title) {
        return jdbcTemplate.query(
                FIND_ACTIVE_TEST_CASE_LIKE_TITLE,
                preparedStatement -> {
                    preparedStatement.setString(1, "%" + title + "%");
                    preparedStatement.setInt(2, size);
                    preparedStatement.setInt(3, offset);
                },
                new TestCaseMapper()
        );
    }

    @Override
    public int getSearchedTotalElements(String title) {
        return geySingleIntElement("%" + title + "%", GET_AMOUNT_OF_ACTIVE_SEARCHED_TEST_CASES);
    }

    @Override
    public List<TestCase> findAllTestCasesByTitlePaginated(int offset, int size, String title) {
        return jdbcTemplate.query(
                FIND_ALL_TEST_CASE_LIKE_TITLE,
                preparedStatement -> {
                    preparedStatement.setString(1, "%" + title + "%");
                    preparedStatement.setInt(2, size);
                    preparedStatement.setInt(3, offset);
                },
                new TestCaseMapper()
        );
    }

    @Override
    public int getSearchedAllTotalElements(String title) {
        return geySingleIntElement("%" + title + "%", GET_AMOUNT_OF_ALL_SEARCHED_TEST_CASES);
    }

    @Override
    public int createTestCase(TestCase testCase) {
        jdbcTemplate.update(
                CREATE_TEST_CASE_SQL,
                testCase.getTitle(),
                testCase.getProjectId(),
                testCase.getStatus().toString(),
                testCase.getResult().toString()
        );

        return geySingleIntElement(testCase.getTitle(), GET_TEST_CASE_ID_BY_TITLE);
    }

    private int geySingleIntElement(String title, String getAmountOfAllSearchedTestCases) {
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
