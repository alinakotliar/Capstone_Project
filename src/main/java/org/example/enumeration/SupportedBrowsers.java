package org.example.enumeration;

import org.example.invoker.WebDriverInvoker;
import org.example.invoker.implementations.ChromeInvoker;
import org.openqa.selenium.WebDriver;

public enum SupportedBrowsers {
    CHROME(new ChromeInvoker());

    private WebDriverInvoker webDriverInvoker;

    SupportedBrowsers(WebDriverInvoker webDriverInvoker) {
        this.webDriverInvoker = webDriverInvoker;
    }

    public WebDriver getWebDriver() {
        return webDriverInvoker.invokeWebDriver();
    }
}