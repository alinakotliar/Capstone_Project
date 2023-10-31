package org.example.pajeobject;

import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SpotifyLoginPage extends BasePage{
    @FindBy(xpath = "//input[@data-testid='login-username']")
    private WebElement userName;
    @FindBy(xpath = "//input[@data-testid='login-password']")
    private WebElement password;
    @FindBy(xpath = "//span[text()='Войти']")
    private WebElement sighInButton;

    @FindBy(xpath = "//div[@data-encore-id='banner']//span[contains(text(), 'Неправильное имя пользователя или пароль.')]")
    private WebElement errorLabel;
    @FindBy(xpath = "//div[@data-testid='username-error']//p")
    private WebElement usernameErrorLabel;
    @FindBy(xpath = "//div[@data-testid='password-error']")
    private WebElement passwordErrorLabel;
    public SpotifyLoginPage(WebDriver webDriver){
        super(webDriver);
    }
    public SpotifyLoginPage open() {
        webDriver.get("https://accounts.spotify.com/login");
        return this;
    }
    public SpotifyLoginPage fillloginform(String userName, String password) {
        this.userName.sendKeys(userName);
        this.password.sendKeys(password);
        return this;
    }

    public SpotifyUserPage signIn(){
        sighInButton.click();
        return new SpotifyUserPage(webDriver);
    }
    public SpotifyLoginPage clearUsername() throws InterruptedException {
        Actions actions = new Actions(webDriver);
        actions.keyDown(Keys.SHIFT).sendKeys(Keys.HOME).keyUp(Keys.SHIFT);
        actions.sendKeys(Keys.BACK_SPACE);
        actions.perform();
        userName.click();
        return this;
    }

    public SpotifyLoginPage clearPassword() throws InterruptedException {
        Actions actions = new Actions(webDriver);
        actions.keyDown(Keys.SHIFT).sendKeys(Keys.HOME).keyUp(Keys.SHIFT);
        actions.sendKeys(Keys.BACK_SPACE);
        actions.perform();
        password.clear();
        return this;
    }
    public String getErrorMessageWrongCredentials() {
        waitForVisibility(errorLabel);
        return errorLabel.getText();
    }

    public boolean isErrorMessageDisplayedWrongCredentials() {
        try {
            waitForVisibility(errorLabel);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUsernameErrorDisplayed() {
        try {
            waitForVisibility(usernameErrorLabel);
            return true;
        } catch (TimeoutException e) {
            return false; // Return false if the error message doesn't appear within the timeout
        }
    }

    public boolean isPasswordErrorDisplayed() {
        try {
            waitForVisibility(passwordErrorLabel);
            return true;
        } catch (TimeoutException e) {
            return false; // Return false if the error message doesn't appear within the timeout
        }
    }


}
