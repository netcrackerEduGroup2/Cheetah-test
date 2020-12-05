package com.ncedu.cheetahtest.service.testcaseexecution;

import com.ncedu.cheetahtest.entity.selenium.ActionResult;
import com.ncedu.cheetahtest.entity.selenium.ActionResultStatus;
import com.ncedu.cheetahtest.entity.selenium.SeleniumAction;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestCaseExecutorImpl implements TestCaseExecutor {

    ActionExecutorProvider actionExecutorProvider;

    @Autowired
    public TestCaseExecutorImpl(ActionExecutorProvider actionExecutorProvider) {
        this.actionExecutorProvider = actionExecutorProvider;
    }

    @Value("${webdriver.chrome.bin}")
    private String googleChromeBin = "/usr/bin/google-chrome";

    @Override
    public List<ActionResult> execute(List<SeleniumAction> actions) {
        WebDriver webDriver = getWebDriver();
        List<ActionResult> actionResultList = new ArrayList<>();

        for (SeleniumAction action: actions) {
            String type = action.getActionType();

            ActionExecutor actionExecutor = actionExecutorProvider.getActionExecutor(type);
            ActionResult actionResult = new ActionResult();

            if (actionExecutor == null) {
                actionResult.setAction(action);
                actionResult.setStatus(ActionResultStatus.FAIL);
                actionResult.setResultDescription("Invalid action name");
                actionResultList.add(actionResult);

                break;
            }

            actionResult =  actionExecutor.execute(action, webDriver);

            File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);

            // TODO add screenshot saving and getting a link on it
            actionResult.setScreenshotUrl("/screenshot/url");

            actionResultList.add(actionResult);

            if (actionResult.getStatus() == ActionResultStatus.FAIL) {
                break;
            }
        }

        webDriver.close();
        webDriver.quit();

        return actionResultList;
    }

    private WebDriver getWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.setBinary(googleChromeBin);
        options.addArguments("--headless", "--disable-gpu", "--no-sandbox");

        return new ChromeDriver(options);
    }
}
