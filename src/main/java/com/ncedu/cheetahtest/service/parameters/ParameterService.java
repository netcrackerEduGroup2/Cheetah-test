package com.ncedu.cheetahtest.service.parameters;

import com.ncedu.cheetahtest.entity.parameter.PaginationParameter;
import com.ncedu.cheetahtest.entity.parameter.Parameter;

import java.util.List;

public interface ParameterService {
    PaginationParameter findByType(String type,int idDataSet,int page,int size);
    PaginationParameter findAllByType(String type , int page,int size);
    List<Parameter> findAllByValue(String value, int idTestCase);
    List<Parameter> findAllByIdDataSet(int idDataSet);
    List<Parameter> findAllByIdTestCase(int idTestCase);
    Parameter createParameter(Parameter parameter);
    Parameter editParameter(Parameter parameter,int id);
    void deleteParameter(int id);
}
