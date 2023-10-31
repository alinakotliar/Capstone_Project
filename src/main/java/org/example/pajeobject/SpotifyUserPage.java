package org.example.pajeobject;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class SpotifyUserPage extends BasePage {
    @FindBy(xpath = "//span[text()='Веб-плеер']")
    private WebElement webPlayerButton;
    @FindBy(xpath = "//button[@data-testid='user-widget-link']")
    private WebElement userIconButton;
    @FindBy(xpath = "//a[span[text()='Профиль']]")
    private WebElement profileButton;
    @FindBy(xpath = "//h1[text()='Alina']")
    private WebElement profileUserName;
    @FindBy(xpath = "//button[contains(@aria-label, 'Создать плейлист или папку')]")
    private WebElement addPlaylistButton;
    @FindBy(xpath = "//button[@role='menuitem']//span[contains(@class, 'Type__TypeElement') and contains(text(), 'Создать плейлист')]")
    private WebElement createPlaylistButton;
    @FindBy(xpath = "//div[@data-testid='action-bar-row']//button[@data-testid='more-button']")
    private WebElement contextMenu;
    @FindBy(xpath = "//button[@role='menuitem']//span[@data-encore-id='type' and contains(text(), 'Изменение сведений')]")
    private WebElement editPlaylist;
    @FindBy(xpath = "//input[@data-testid='playlist-edit-details-name-input']")
    private WebElement playlistNameInput;
    @FindBy(xpath = "//button[@data-testid='playlist-edit-details-save-button']")
    private WebElement savePlaylistButton;
    @FindBy(xpath = "//h1[text()='My favourite playlist']")
    private WebElement playlistName;

    @FindBy(xpath = "//button[contains(span, 'Создать плейлист')]")
    private WebElement createFirstPlaylistButton;

    @FindBy(xpath = "//h1[contains(text(), 'Мой плейлист №')]")
    private WebElement playlistNameFromMainMenu;

    @FindBy(xpath = "//p[contains(@id, 'listrow-title-spotify:playlist')]")
    private WebElement playlistNameFromListrow;

    @FindBy(xpath = "//li[@aria-posinset=\"1\"]")
    private WebElement lastPlaylist;
    @FindBy(xpath = "//button[.//span[text()='Удалить']]")
    private WebElement deleteButton;
    @FindBy(xpath = "//button[span[text()='Удалить'] and contains(@aria-label, 'Удалить плейлист')]")
    private WebElement confirmDeleteButton;
    @FindBy(xpath = "//span[@data-encore-id='type' and text()='Удалено из медиатеки']")
    private WebElement successDeleteMessage;

    @FindBy(xpath = "//div[@data-encore-id=\"popover\"]")
    private WebElement dialogWindow;

    @FindBy(xpath = "//button[@aria-label=\"Закрыть\" and @data-encore-id=\"buttonTertiary\"]")
    private WebElement closeDialogWindowButton;



    public SpotifyUserPage(WebDriver webDriver) {
        super(webDriver);

    }
    public SpotifyUserPage navigateToWebPlayer() {
        waitForVisibility(webPlayerButton);
        webPlayerButton.click();
        return this;
    }
    public SpotifyUserPage logToProfile() {
        waitForVisibility(userIconButton);
        userIconButton.click();
        waitForVisibility(profileButton);
        profileButton.click();

        return this;
    }

    public String getUserInfoText() {
        waitForVisibility(profileUserName);
        return profileUserName.getText();
    }
    public SpotifyUserPage clickCreatePlaylist(){
        waitForVisibility(addPlaylistButton);
        addPlaylistButton.click();
        waitForVisibility(createPlaylistButton);
        createPlaylistButton.click();
        return this;
    }
    public SpotifyUserPage selectEditDetails(String playlistName){
        waitForVisibility(contextMenu);
        contextMenu.click();
        waitForVisibility(editPlaylist);
        editPlaylist.click();
        playlistNameInput.clear();
        playlistNameInput.sendKeys(playlistName);
        savePlaylistButton.click();
        return this;
    }
    public String getPlaylistNameText() {
        waitForVisibility(playlistName);
        return playlistName.getText();
    }
public  SpotifyUserPage createFirstPlaylist(){
        waitForVisibility(createFirstPlaylistButton);
        createFirstPlaylistButton.click();
        return this;
}

    public String getPlaylistNameFromListrow() {
        waitForVisibility(playlistNameFromListrow);
        return playlistNameFromListrow.getText();
    }


    public String getPlaylistNameFromMainMenu() {
        waitForVisibility(playlistNameFromMainMenu);
        return playlistNameFromMainMenu.getText();
    }

    public SpotifyUserPage deletePlaylist() throws InterruptedException {
        Thread.sleep(5000);
        Actions actions = new Actions(webDriver);
        waitForVisibility(lastPlaylist);
        actions.contextClick(lastPlaylist).build().perform();
        waitForVisibility(deleteButton);
        deleteButton.click();
        waitForVisibility(confirmDeleteButton);
        confirmDeleteButton.click();
        return this;
    }

    public boolean isPlaylistDeleted(){
        try {
            waitForVisibility(successDeleteMessage);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
