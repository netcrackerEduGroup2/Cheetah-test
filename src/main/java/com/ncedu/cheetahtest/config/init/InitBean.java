package com.ncedu.cheetahtest.config.init;

import com.ncedu.cheetahtest.service.testcase.scheduling.TestCaseScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InitBean implements InitializingBean {
    private final TestCaseScheduler testCaseScheduler;

    @Override
    public void afterPropertiesSet() {
        testCaseScheduler.scheduleTestCases();
    }
}
