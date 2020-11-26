package com.ncedu.cheetahtest.service.dataset;

import com.ncedu.cheetahtest.dao.dataset.DataSetDao;
import com.ncedu.cheetahtest.entity.dataset.DataSet;
import com.ncedu.cheetahtest.entity.dataset.PaginationDataset;
import com.ncedu.cheetahtest.exception.helpers.PaginationWrongInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSetServiceImpl implements DataSetService{
    private final DataSetDao dataSetDao;
    @Autowired
    public DataSetServiceImpl(DataSetDao dataSetDao){
        this.dataSetDao = dataSetDao;
    }

    @Override
    public PaginationDataset findByTitleLike(String title, int idTestCase, int size, int page) {
        int totalDataSets = dataSetDao.getTotalElements(idTestCase);

        if(size*page<totalDataSets){
            PaginationDataset paginationDataset = new PaginationDataset();
            paginationDataset.setTotalElements(totalDataSets);
            paginationDataset.setDataSets(dataSetDao.findByTitleLike(title,idTestCase,size, size*(page-1)));
            return paginationDataset;
        }
        else throw new PaginationWrongInputException();
    }

    @Override
    public DataSet createDataSet(DataSet dataSet) {
        return dataSetDao.createDataSet(dataSet);
    }

    @Override
    public DataSet editDataSet(DataSet dataSet, int id) {
        return dataSetDao.editDataSet(dataSet,id);
    }

    @Override
    public void deleteDataSet(int id) {
        //TODO find how to check if person is admin in spring security
        dataSetDao.deleteDataSet(id);
    }
}
