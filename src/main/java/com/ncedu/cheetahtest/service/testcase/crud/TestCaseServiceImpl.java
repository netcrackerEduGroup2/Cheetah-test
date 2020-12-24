package com.ncedu.cheetahtest.service.testcase.crud;

import com.ncedu.cheetahtest.dao.genericdao.AbstractActiveDao;
import com.ncedu.cheetahtest.dao.project.ProjectDao;
import com.ncedu.cheetahtest.dao.testcase.TestCaseDao;
import com.ncedu.cheetahtest.entity.pagination.PaginationContainer;
import com.ncedu.cheetahtest.entity.project.Project;
import com.ncedu.cheetahtest.entity.testcase.TestCase;
import com.ncedu.cheetahtest.entity.testcase.TestCaseResult;
import com.ncedu.cheetahtest.entity.testcase.TestCaseScheduleDto;
import com.ncedu.cheetahtest.exception.general.InvalidParametersException;
import com.ncedu.cheetahtest.exception.project.ProjectNotFoundException;
import com.ncedu.cheetahtest.exception.testcase.TestCaseAlreadyExistsException;
import com.ncedu.cheetahtest.exception.testcase.TestCaseNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestCaseServiceImpl implements TestCaseService {

  private final TestCaseDao testCaseDao;
  private final AbstractActiveDao<TestCase> testCaseGenDao;
  private final ProjectDao projectDao;

  @Override
  @Transactional
  public PaginationContainer<TestCase> getTestCases(int page, int size) {
    List<TestCase> testCaseList = testCaseGenDao.getActivePaginated(page, size);
    int totalElements = testCaseGenDao.getAmountActiveElements();

    return new PaginationContainer<>(testCaseList, totalElements);
  }

  @Override
  @Transactional
  public PaginationContainer<TestCase> getAllTestCases(int page, int size) {

    List<TestCase> testCaseList = testCaseGenDao.getAllPaginated(page, size);
    int totalElements = testCaseGenDao.getAmountAllElements();

    return new PaginationContainer<>(testCaseList, totalElements);
  }

  @Override
  @Transactional
  public void save(TestCase testCase) {
    TestCase testCaseWithSameTitle = testCaseDao
        .findTestCaseByTitleExceptCurrent(
            testCase.getTitle(),
            testCase.getId());

    if (testCaseWithSameTitle == null) {
      testCaseDao.save(testCase);
    } else {
      throw new TestCaseAlreadyExistsException();
    }
  }

  @Override
  @Transactional
  public TestCase findTestCaseByProjectIdAndTestCaseId(int projectId, int id) {
    TestCase testCase = testCaseDao.findTestCaseByProjectIdAndTestCaseId(projectId, id);

    if (testCase == null) {
      throw new TestCaseNotFoundException();
    }

    return testCase;
  }

  @Override
  @Transactional
  public void deactivateTestCase(int id) {
    testCaseDao.deactivate(id);
  }

  @Override
  public PaginationContainer<TestCase> findTestCasesByTitlePaginated(int page, int size, String title) {
    List<TestCase> testCaseList = testCaseGenDao
        .findActiveByTitlePaginated(page, size, title);

    int totalElements = testCaseGenDao.getSearchedActiveTotalElements(title);

    return new PaginationContainer<>(testCaseList, totalElements);
  }

  @Override
  @Transactional
  public PaginationContainer<TestCase> findAllTestCasesByTitlePaginated(
      int page, int size, String title) {

    List<TestCase> testCaseList = testCaseGenDao
        .findAllByTitlePaginated(page, size, title);

    int totalElements = testCaseGenDao.getSearchedAllTotalElements(title);

    return new PaginationContainer<>(testCaseList, totalElements);
  }

  @Override
  @Transactional
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
      throw new TestCaseAlreadyExistsException();
    }

    return testCaseDao.createTestCase(testCase);
  }

  @Override
  @Transactional
  public PaginationContainer<TestCase> getActiveTestCasesPaginatedByProjectId(int page, int size, int projectId) {
    List<TestCase> testCaseList = testCaseDao.getActiveTestCasesPaginatedByProjectId(page, size, projectId);
    int totalElements = testCaseDao.getAmountActiveElementsByProjectId(projectId);

    return new PaginationContainer<>(testCaseList, totalElements);
  }

  @Override
  @Transactional
  public PaginationContainer<TestCase> findTestCasesByTitlePaginatedAndByProjectId(int page, int size, String keyword,
      int projectId) {
    List<TestCase> testCaseList = testCaseDao
        .findTestCasesByTitlePaginatedAndByProjectId(page, size, keyword, projectId);

    int totalElements = testCaseDao.getAmountByTitlePaginatedAndByProjectId(keyword, projectId);
    return new PaginationContainer<>(testCaseList, totalElements);
  }

  @Override
  @Transactional
  public void updateExecutionCronDateAndRepeatability(TestCaseScheduleDto testCaseScheduleDto) {
    testCaseDao.updateExecutionCronDateAndRepeatability(testCaseScheduleDto);
  }

  @Override
  public void deleteExecutionCronDateAndRepeatability(int testCaseId) {
    testCaseDao.deleteExecutionCronDateAndRepeatability(testCaseId);
  }

  @Override
  public List<TestCase> getActiveTestCasesWithExecutionDate() {
    return testCaseDao.getActiveTestCasesWithExecutionDate();
  }

  @Override
  public List<TestCase> getAllActiveTestCasesByTitle(String title) {
    return testCaseDao.getAllActiveTestCasesByTitle(title);
  }

  @Override
  @Transactional
  public PaginationContainer<TestCase> findTestCasesByTitlePaginatedAndByProjectIdAndResult(
          int page, int size, String keyword, String result, int projectId) {
    try {
      TestCaseResult testCaseResult = TestCaseResult.valueOf(result);
      List<TestCase> testCaseList = testCaseDao
              .findTestCasesByTitlePaginatedAndByProjectIdAndResult(page, size, keyword, testCaseResult, projectId);

      int totalElements = testCaseDao.getAmountByTitlePaginatedAndByProjectIdAndResult(keyword, testCaseResult, projectId);
      return new PaginationContainer<>(testCaseList, totalElements);

    } catch (IllegalArgumentException e) {
      throw new InvalidParametersException();
    }
  }
}

