package com.ncedu.cheetahtest.service.testcaseexecution;

public interface ActionExecutorProvider {
    ActionExecutor getActionExecutor(String actionType);
}
