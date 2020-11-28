package com.ncedu.cheetahtest.service.testcase;

import com.ncedu.cheetahtest.dao.project.ProjectDao;
import com.ncedu.cheetahtest.entity.project.Project;
import com.ncedu.cheetahtest.entity.testcase.TestCase;
import com.ncedu.cheetahtest.entity.testcase.TestCasePaginated;
import com.ncedu.cheetahtest.exception.project.ProjectNotFoundException;
import com.ncedu.cheetahtest.exception.testcase.InvalidParametersException;
import com.ncedu.cheetahtest.exception.testcase.TestCaseAlreadyExistException;
import com.ncedu.cheetahtest.exception.testcase.TestCaseNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestCaseServiceImpl implements TestCaseService {

    private final GTestCaseDaoImpl testCaseDao;
    private final ProjectDao projectDao;

    @Override
    public TestCasePaginated getTestCases(int page, int size) {
        int offset = getOffset(page, size);

        List<TestCase> testCaseList = testCaseDao.getActivePaginated(offset, size);
        int totalElements = testCaseDao.getAmountActiveElements();

        return new TestCasePaginated(testCaseList, totalElements);
    }

    @Override
    public TestCasePaginated getAllTestCases(int page, int size) {
        int offset = getOffset(page, size);

        List<TestCase> testCaseList = testCaseDao.getAllPaginated(offset, size);
        int totalElements = testCaseDao.getAmountAllElements();

        return new TestCasePaginated(testCaseList, totalElements);
    }

    @Override
    public void save(TestCase testCase) {
        TestCase testCaseWithSameTitle = testCaseDao
                .findTestCaseByTitleExceptCurrent(
                        testCase.getTitle(),
                        testCase.getId());

        if (testCaseWithSameTitle == null) {
            testCaseDao.save(testCase);
        } else {
            throw new TestCaseAlreadyExistException();
        }
    }

    @Override
    public TestCase findTestCaseById(int id) {
        TestCase testCase = testCaseDao.findById(id);

        if (testCase == null) {
            throw new TestCaseNotFoundException();
        }

        return testCase;
    }

    @Override
    public void deactivateTestCase(int id) {
        testCaseDao.deactivate(id);
    }

    @Override
    public TestCasePaginated findTestCasesByTitlePaginated(int page, int size, String title) {
        int offset = getOffset(page, size);

        List<TestCase> testCaseList = testCaseDao
                .findByTitlePaginated(offset, size, title);

        int totalElements = testCaseDao.getSearchedTotalElements(title);

        return new TestCasePaginated(testCaseList, totalElements);
    }

    @Override
    public TestCasePaginated findAllTestCasesByTitlePaginated(
            int page, int size, String title) {

        int offset = getOffset(page, size);

        List<TestCase> testCaseList = testCaseDao
                .findAllByTitlePaginated(offset, size, title);

        int totalElements = testCaseDao.getSearchedAllTotalElements(title);

        return new TestCasePaginated(testCaseList, totalElements);
    }

    @Override
    public int createTestCase(TestCase testCase) {
        TestCase testCaseWithSameTitle = testCaseDao
                .findTestCaseByTitleExceptCurrent(
                        testCase.getTitle(),
                        testCase.getId());

        Project project = projectDao.findByProjectId(testCase.getProjectId());

        if (project == null) {
            throw new ProjectNotFoundException();
        }
        if (testCaseWithSameTitle != null) {
            throw new TestCaseAlreadyExistException();
        }

        return testCaseDao.createTestCase(testCase);
    }

    private int getOffset(int page, int size) {
        if (page <= 0 || size < 0) {
            throw new InvalidParametersException();
        }

        return size * (page - 1);
    }
}

