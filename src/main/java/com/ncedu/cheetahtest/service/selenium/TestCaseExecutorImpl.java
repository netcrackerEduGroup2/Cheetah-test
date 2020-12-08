package com.ncedu.cheetahtest.service.selenium;

import com.ncedu.cheetahtest.entity.selenium.ActionResult;
import com.ncedu.cheetahtest.entity.selenium.ActionResultStatus;
import com.ncedu.cheetahtest.entity.selenium.SeleniumAction;
import com.ncedu.cheetahtest.service.cloud.AmazonClientService;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TestCaseExecutorImpl implements TestCaseExecutor {

    private AmazonClientService amazonClientService;
    private ActionExecutorProvider actionExecutorProvider;
    private WebDriver webDriver;

    @Autowired
    public TestCaseExecutorImpl(AmazonClientService amazonClientService, ActionExecutorProvider actionExecutorProvider, WebDriver webDriver) {
        this.amazonClientService = amazonClientService;
        this.actionExecutorProvider = actionExecutorProvider;
        this.webDriver = webDriver;
    }

    @Override
    public ActionResult executeAction(SeleniumAction action) {

        String type = action.getActionType();

        ActionExecutor actionExecutor = actionExecutorProvider.getActionExecutor(type);
        ActionResult actionResult = new ActionResult();

        if (actionExecutor == null) {
            actionResult.setAction(action);
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("Invalid action name");

            return actionResult;
        }

        actionResult = actionExecutor.execute(action, webDriver);

        File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        String screenshotUrl = amazonClientService.uploadScreenshot(screenshot);
        actionResult.setScreenshotUrl(screenshotUrl);

        return actionResult;
    }

    @Override
    public void close() {
        webDriver.close();
        webDriver.quit();
    }
}
