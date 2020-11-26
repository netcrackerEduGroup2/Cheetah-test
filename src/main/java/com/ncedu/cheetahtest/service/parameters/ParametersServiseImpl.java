package com.ncedu.cheetahtest.service.parameters;

import com.ncedu.cheetahtest.dao.parameters.ParametersDao;
import com.ncedu.cheetahtest.entity.parameter.PaginationParameter;
import com.ncedu.cheetahtest.entity.parameter.Parameter;
import com.ncedu.cheetahtest.exception.helpers.PaginationWrongInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParametersServiseImpl implements ParameterService {
    private final ParametersDao parametersDao;

    @Autowired
    public ParametersServiseImpl(ParametersDao parametersDao) {
        this.parametersDao = parametersDao;
    }

    @Override
    public PaginationParameter findByType(String type, int idDataSet, int page, int size) {
        int totalParameters = parametersDao.getTotalElements(idDataSet);

        if(size*page<totalParameters) {
            PaginationParameter paginationParameter = new PaginationParameter();
            paginationParameter.setTotalParameters(totalParameters);
            paginationParameter.setParameters(
                    parametersDao.findByTypeLike(type, idDataSet, size, size * (page - 1))
            );
            return paginationParameter;
        }
        else throw new PaginationWrongInputException();
    }

    @Override
    public Parameter createParameter(Parameter parameter) {
        return parametersDao.createParameter(parameter);
    }

    @Override
    public Parameter editParameter(Parameter parameter, int id) {
        return parametersDao.editParameter(parameter, id);
    }

    @Override
    public void deleteParameter(int id) {
        //todo find out how to define in spring security, is an user is admin
        parametersDao.deleteParameter(id);
    }
}
