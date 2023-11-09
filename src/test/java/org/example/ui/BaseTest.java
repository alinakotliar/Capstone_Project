package org.example.ui;

import org.example.factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {
    protected WebDriver webDriver = new WebDriverFactory().getWebDriver();

    protected void setUpWebDriver() {
        webDriver.manage().window().maximize();
    }

    protected void quit() {
        webDriver.quit();
    }
}
