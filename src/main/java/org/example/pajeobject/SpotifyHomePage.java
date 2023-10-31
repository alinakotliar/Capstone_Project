package org.example.pajeobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SpotifyHomePage extends BasePage {
    @FindBy(xpath = "//button[@data-testid='login-button']")
    private WebElement profileInfoButton;
    public SpotifyHomePage(WebDriver webDriver){
        super(webDriver);
    }
    public SpotifyHomePage openLoginForm() {
        webDriver.get("https://open.spotify.com/");
        return this;
    }
    public SpotifyHomePage openProfileInfoModule() {
        profileInfoButton.click();

        return new SpotifyHomePage(webDriver);
    }
}

