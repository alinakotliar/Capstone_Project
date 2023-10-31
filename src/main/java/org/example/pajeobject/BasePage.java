package org.example.pajeobject;

import org.example.waits.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver webDriver;
    protected WebDriverWait webDriverWait;

    protected BasePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        PageFactory.initElements(webDriver, this);
    }
    public void waitForVisibility(WebElement element) {
        WaitUtils.waitForVisibility(webDriver, element);
    }

}
