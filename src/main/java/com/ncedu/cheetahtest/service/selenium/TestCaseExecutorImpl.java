package com.ncedu.cheetahtest.service.selenium;

import com.ncedu.cheetahtest.entity.selenium.ActionResult;
import com.ncedu.cheetahtest.entity.selenium.ActionResultStatus;
import com.ncedu.cheetahtest.entity.selenium.SeleniumAction;
import com.ncedu.cheetahtest.service.cloud.AmazonClientService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
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

        waitForPageLoaded();
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

    public void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation =
                driver -> ((JavascriptExecutor) Objects.requireNonNull(driver))
                        .executeScript("return document.readyState")
                        .toString().equals("complete");
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(webDriver, 30);
            wait.until(expectation);
        } catch (Throwable error) {
            log.error("Timeout waiting for Page Load Request to complete.");
        }
    }
}
