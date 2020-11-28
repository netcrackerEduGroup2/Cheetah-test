package com.ncedu.cheetahtest.service.testcase;

import com.ncedu.cheetahtest.dao.genericdao.AbstractDao;
import com.ncedu.cheetahtest.dao.testcase.TestCaseMapper;
import com.ncedu.cheetahtest.entity.testcase.TestCase;
import com.ncedu.cheetahtest.exception.testcase.TestCaseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ncedu.cheetahtest.service.testcase.GTestCaseConsts.*;

@Repository
public class GTestCaseDaoImpl extends AbstractDao<TestCase> implements GTestCaseDao {

    @Autowired
    public GTestCaseDaoImpl(JdbcTemplate jdbcTemplate) {
        super(new TestCaseMapper(), jdbcTemplate, new GTestCaseConsts());
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
    public int createTestCase(TestCase testCase) {
        jdbcTemplate.update(
                CREATE_TEST_CASE_SQL,
                testCase.getTitle(),
                testCase.getProjectId(),
                testCase.getStatus().toString(),
                testCase.getResult().toString()
        );

        return getSingleIntElement(testCase.getTitle(), GET_TEST_CASE_ID_BY_TITLE);
    }
}
