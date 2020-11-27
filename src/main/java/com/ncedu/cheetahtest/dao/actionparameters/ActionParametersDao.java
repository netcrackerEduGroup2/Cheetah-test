package com.ncedu.cheetahtest.dao.actionparameters;

import com.ncedu.cheetahtest.entity.actionparameters.ActionParameter;

public interface ActionParametersDao {
   ActionParameter createActionParam(ActionParameter actionParameter);
   ActionParameter findById(int id);
   void deleteByIdActParam(int idActParam);


}
