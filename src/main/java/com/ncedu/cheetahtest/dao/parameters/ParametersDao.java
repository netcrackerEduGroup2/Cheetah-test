package com.ncedu.cheetahtest.dao.parameters;

import com.ncedu.cheetahtest.entity.parameter.Parameter;

import java.util.List;

public interface ParametersDao {
    Parameter findById(int id);
    List<Parameter> findByTypeLike(String type, int idDataSet, int limit, int offset);
    List<Parameter> findAllByType(String type, int limit, int offset);
    List<Parameter> findAllByIdDataSet(int idDataSet);
    List<Parameter> findAllByIdTestCase(int idTestCase);
    Parameter createParameter(Parameter parameter);
    Parameter editParameter(Parameter parameter, int id);
    void deleteParameter(int id);
    void deleteByIdDataSet(int idDataSet);
    int getTotalElements(int idDataSet, String type);
    int getTotalAllElements(String type);
    Parameter findByTypeAndIdDataSet(String type, int idDataSet);

}
