package com.ncedu.cheetahtest.service.selenium;

import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.entity.selenium.ActionResult;
import com.ncedu.cheetahtest.entity.selenium.ActionResultStatus;
import com.ncedu.cheetahtest.entity.selenium.SeleniumAction;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ActionExecutorProviderImpl implements ActionExecutorProvider {

    private Map<String, ActionExecutor> actionMap;

    public ActionExecutorProviderImpl() {
        actionMap = new HashMap<>();

        actionMap.put("getPage", this::getPage);
        actionMap.put("checkTitle", this::checkTitle);
        actionMap.put("checkUrl", this::checkUrl);
        actionMap.put("goBack", this::goBack);
        actionMap.put("goForward", this::goForward);

        actionMap.put("hoverElementById", this::hoverElementById);
        actionMap.put("hoverElementByClassName", this::hoverElementByClassName);
        actionMap.put("hoverElementByCssSelector", this::hoverElementByCssSelector);

        actionMap.put("checkElementPresenceById", this::checkElementPresenceById);
        actionMap.put("checkElementPresenceClassName", this::checkElementPresenceClassName);
        actionMap.put("checkElementPresenceByCssSelector", this::checkElementPresenceByCssSelector);

        actionMap.put("clickElementById", this::clickElementById);
        actionMap.put("clickElementByClassName", this::clickElementByClassName);
        actionMap.put("clickElementByCssSelector", this::clickElementByCssSelector);


    }

    @Override
    public ActionExecutor getActionExecutor(String actionType) {
        return actionMap.get(actionType);
    }

    private ActionResult getPage(SeleniumAction action, WebDriver webDriver) {
        ActionResult actionResult = new ActionResult();
        actionResult.setAction(action);

        if (action.getArgument() == null) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("Missing argument.");

            return actionResult;
        }

        try {
            webDriver.get(action.getArgument());
        } catch (WebDriverException e) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("This ULR can’t be reached.");

            return actionResult;
        }

        actionResult.setStatus(ActionResultStatus.SUCCESS);
        actionResult.setResultDescription("Action was successfully executed.");

        return actionResult;
    }

    private ActionResult checkTitle(SeleniumAction action, WebDriver webDriver) {
        ActionResult actionResult = new ActionResult();
        actionResult.setAction(action);

        if (action.getArgument() == null) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("Missing argument.");

            return actionResult;
        }

        if (webDriver.getTitle().equals(action.getArgument())) {
            actionResult.setStatus(ActionResultStatus.SUCCESS);
            actionResult.setResultDescription("The titles match.");
        } else {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("The titles do not match.");
        }

        return actionResult;
    }

    private ActionResult checkUrl(SeleniumAction action, WebDriver webDriver) {
        ActionResult actionResult = new ActionResult();
        actionResult.setAction(action);

        if (action.getArgument() == null) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("Missing argument.");

            return actionResult;
        }

        if (webDriver.getCurrentUrl().equals(action.getArgument())) {
            actionResult.setStatus(ActionResultStatus.SUCCESS);
            actionResult.setResultDescription("The URLs match.");
        } else {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("The URLs do not match.");
        }

        return actionResult;
    }

    private ActionResult goBack(SeleniumAction action, WebDriver webDriver) {
        ActionResult actionResult = new ActionResult();
        actionResult.setAction(action);

        try {
            webDriver.navigate().back();
        } catch (WebDriverException e) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("It is not possible to go back");

            return actionResult;
        }

        actionResult.setStatus(ActionResultStatus.SUCCESS);
        actionResult.setResultDescription("Action was successfully executed");

        return actionResult;
    }

    private ActionResult goForward(SeleniumAction action, WebDriver webDriver) {
        ActionResult actionResult = new ActionResult();
        actionResult.setAction(action);

        try {
            webDriver.navigate().forward();
        } catch (WebDriverException e) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("It is not possible to go forward");

            return actionResult;
        }

        actionResult.setStatus(ActionResultStatus.SUCCESS);
        actionResult.setResultDescription("Action was successfully executed");

        return actionResult;
    }

    private ActionResult hoverElementById(SeleniumAction action, WebDriver webDriver) {
        ActionResult actionResult = new ActionResult();
        actionResult.setAction(action);

        if (action.getElement() == null) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("Missing element identifier.");

            return actionResult;
        }

        try {
            WebElement webElement = webDriver.findElement(By.id(action.getElement()));

            Actions actions = new Actions(webDriver);
            actions.moveToElement(webElement).build().perform();

            actionResult.setResultDescription("Action was successfully executed.");
            actionResult.setStatus(ActionResultStatus.SUCCESS);

            return actionResult;
        } catch (WebDriverException e) {
            actionResult.setResultDescription("Action execution failed. Can't find such element.");
            actionResult.setStatus(ActionResultStatus.FAIL);

            return actionResult;
        }
    }

    private ActionResult hoverElementByClassName(SeleniumAction action, WebDriver webDriver) {
        ActionResult actionResult = new ActionResult();
        actionResult.setAction(action);

        if (action.getElement() == null) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("Missing element identifier.");

            return actionResult;
        }

        try {
            WebElement webElement = webDriver.findElement(By.className(action.getElement()));

            Actions actions = new Actions(webDriver);
            actions.moveToElement(webElement).build().perform();

            actionResult.setResultDescription("Action was successfully executed.");
            actionResult.setStatus(ActionResultStatus.SUCCESS);

            return actionResult;
        } catch (WebDriverException e) {
            actionResult.setResultDescription("Action execution failed. Can't find such element.");
            actionResult.setStatus(ActionResultStatus.FAIL);

            return actionResult;
        }
    }

    private ActionResult hoverElementByCssSelector(SeleniumAction action, WebDriver webDriver) {
        ActionResult actionResult = new ActionResult();
        actionResult.setAction(action);

        if (action.getElement() == null) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("Missing element identifier.");

            return actionResult;
        }

        try {
            WebElement webElement = webDriver.findElement(By.cssSelector(action.getElement()));

            Actions actions = new Actions(webDriver);
            actions.moveToElement(webElement).build().perform();

            actionResult.setResultDescription("Action was successfully executed.");
            actionResult.setStatus(ActionResultStatus.SUCCESS);

            return actionResult;
        } catch (WebDriverException e) {
            actionResult.setResultDescription("Action execution failed. Can't find such element.");
            actionResult.setStatus(ActionResultStatus.FAIL);

            return actionResult;
        }
    }

    private ActionResult checkElementPresenceById(SeleniumAction action, WebDriver webDriver) {
        ActionResult actionResult = new ActionResult();
        actionResult.setAction(action);

        if (action.getElement() == null) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("Missing element identifier.");

            return actionResult;
        }

        try {
            WebElement webElement = webDriver.findElement(By.id(action.getElement()));

            return checkElementPresence(action, webElement);
        } catch (WebDriverException e) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("Action execution failed. Can't find such element.");

            return actionResult;
        }    }

    private ActionResult checkElementPresenceClassName(SeleniumAction action, WebDriver webDriver) {
        ActionResult actionResult = new ActionResult();
        actionResult.setAction(action);

        if (action.getElement() == null) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("Missing element identifier.");

            return actionResult;
        }

        try {
            WebElement webElement = webDriver.findElement(By.className(action.getElement()));

            return checkElementPresence(action, webElement);
        } catch (WebDriverException e) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("Action execution failed. Can't find such element.");

            return actionResult;
        }
    }

    private ActionResult checkElementPresenceByCssSelector(SeleniumAction action, WebDriver webDriver) {
        ActionResult actionResult = new ActionResult();
        actionResult.setAction(action);

        if (action.getElement() == null) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("Missing element identifier.");

            return actionResult;
        }

        try {
            WebElement webElement = webDriver.findElement(By.cssSelector(action.getElement()));

            return checkElementPresence(action, webElement);
        } catch (WebDriverException e) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("Action execution failed. Can't find such element.");

            return actionResult;
        }
    }

    private ActionResult checkElementPresence(SeleniumAction action, WebElement webElement) {
        ActionResult actionResult = new ActionResult();

        actionResult.setAction(action);
        actionResult.setResultDescription("Success");
        actionResult.setStatus(ActionResultStatus.SUCCESS);

        return actionResult;
    }

    private ActionResult clickElementById(SeleniumAction action, WebDriver webDriver) {
        ActionResult actionResult = new ActionResult();
        actionResult.setAction(action);

        if (action.getElement() == null) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("Missing element identifier.");

            return actionResult;
        }

        try {
            WebElement webElement = webDriver.findElement(By.id(action.getElement()));

            Actions actions = new Actions(webDriver);
            actions.moveToElement(webElement).click().build().perform();

            actionResult.setResultDescription("Action was successfully executed.");
            actionResult.setStatus(ActionResultStatus.SUCCESS);

            return actionResult;
        } catch (WebDriverException e) {
            actionResult.setResultDescription("Action execution failed. Can't find such element.");
            actionResult.setStatus(ActionResultStatus.FAIL);

            return actionResult;
        }
    }

    private ActionResult clickElementByClassName(SeleniumAction action, WebDriver webDriver) {
        ActionResult actionResult = new ActionResult();
        actionResult.setAction(action);

        if (action.getElement() == null) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("Missing element identifier.");

            return actionResult;
        }

        try {
            WebElement webElement = webDriver.findElement(By.className(action.getElement()));

            Actions actions = new Actions(webDriver);
            actions.moveToElement(webElement).click().build().perform();

            actionResult.setResultDescription("Action was successfully executed.");
            actionResult.setStatus(ActionResultStatus.SUCCESS);

            return actionResult;
        } catch (WebDriverException e) {
            actionResult.setResultDescription("Action execution failed. Can't find such element.");
            actionResult.setStatus(ActionResultStatus.FAIL);

            return actionResult;
        }
    }

    private ActionResult clickElementByCssSelector(SeleniumAction action, WebDriver webDriver) {
        ActionResult actionResult = new ActionResult();
        actionResult.setAction(action);

        if (action.getElement() == null) {
            actionResult.setStatus(ActionResultStatus.FAIL);
            actionResult.setResultDescription("Missing element identifier.");

            return actionResult;
        }

        try {
            WebElement webElement = webDriver.findElement(By.cssSelector(action.getElement()));

            Actions actions = new Actions(webDriver);
            actions.moveToElement(webElement).click().build().perform();

            actionResult.setResultDescription("Action was successfully executed.");
            actionResult.setStatus(ActionResultStatus.SUCCESS);

            return actionResult;
        } catch (WebDriverException e) {
            actionResult.setResultDescription("Action execution failed. Can't find such element.");
            actionResult.setStatus(ActionResultStatus.FAIL);

            return actionResult;
        }
    }

    private ActionResult fillById(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult fillByClassname(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult fillByCssSelector(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult checkElementContentById(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult checkElementContentClassName(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult checkElementContentCssSelector(SeleniumAction action, WebDriver webDriver) {
        return null;
    }

    private ActionResult pressEnter(SeleniumAction action, WebDriver webDriver) {
        return null;
    }
}
