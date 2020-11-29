package com.ncedu.cheetahtest.dao.genericdao;

import java.util.List;

public interface AbstractDao<T> {
    List<T> getActivePaginated(int offset, int size);

    List<T> getAllPaginated(int offset, int size);

    int getAmountActiveElements();

    int getAmountAllElements();

    T findById(int id);

    int getSearchedActiveTotalElements(String title);

    int getSearchedAllTotalElements(String title);

    List<T> findActiveByTitlePaginated(int offset, int size, String title);

    List<T> findAllByTitlePaginated(int offset, int size, String title);

    int getSingleIntElement(String title, String getAmountOfAllSearchedTestCases);
}
