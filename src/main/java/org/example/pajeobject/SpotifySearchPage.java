package org.example.pajeobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.NoSuchElementException;

public class SpotifySearchPage extends BasePage {
    @FindBy(xpath = "//input[@placeholder='Что хочешь послушать?']")
    private WebElement searchInputField;
    @FindBy(xpath = "//button[@aria-checked='false' and span[text()='Треки']]")
    private WebElement trackTab;
    @FindBy(xpath = "//div[@role='row' and @aria-rowindex='3']")
    private WebElement trackId;
    @FindBy(xpath = "//div[@role='row' and @aria-rowindex='8']")
    private WebElement secondtrackId;
    @FindBy(xpath = "//a[contains(@aria-label, 'Поиск')]")
    private WebElement searchIcon;
    @FindBy(xpath = "//span[text()='Добавить в плейлист']")
    private WebElement addToPlaylistMenu;
    @FindBy(xpath = "//li[contains(@class, 'DuEPSADpSwCcO880xjUG')][2]")
    private WebElement newCreatedPlaylistID;
    @FindBy(xpath = "//li[contains(@aria-posinset, '1')]")
    private WebElement newCreatedPlaylistTab;
    @FindBy(xpath = "//div[@data-testid='playlist-tracklist']")
    private WebElement tracklist;
    @FindBy(xpath = "//div[@role=\"gridcell\" and @aria-colindex=\"5\"]")
    private WebElement trackIdInList;
    //div[@role='row'][@aria-rowindex='2']
    @FindBy(xpath = "//span[text()='Удалить из этого плейлиста']")
    private WebElement deleteOption;
    @FindBy(xpath = "//button[contains(@aria-label, 'Открыть контекстное меню: Whitney Houston')]")
    private WebElement contextMenuButton;



    public SpotifySearchPage(WebDriver webDriver){
        super(webDriver);
    }

    public SpotifySearchPage clickSearchIcon() throws InterruptedException {
        Thread.sleep(5000);
        searchIcon.click();
        return this;

    }
    public SpotifySearchPage searchInput(String searchInput) {
        waitForVisibility(searchInputField);
        searchInputField.sendKeys(searchInput);
        waitForVisibility(trackTab);
        trackTab.click();
        return this;
}
    public SpotifySearchPage addTrackToPlaylist(){
        Actions actions = new Actions(webDriver);
        waitForVisibility(trackId);
        actions.contextClick(trackId).build().perform();
        waitForVisibility(addToPlaylistMenu);
        addToPlaylistMenu.click();
        waitForVisibility(newCreatedPlaylistID);
        newCreatedPlaylistID.click();
        return this;
    }

    public SpotifySearchPage addSecondTrackToPlaylist(){
        Actions actions = new Actions(webDriver);
        waitForVisibility(secondtrackId);
        actions.contextClick(secondtrackId).build().perform();
        waitForVisibility(addToPlaylistMenu);
        addToPlaylistMenu.click();
        waitForVisibility(newCreatedPlaylistID);
        newCreatedPlaylistID.click();
        return this;
    }
    public SpotifySearchPage goToPlaylist(){
    waitForVisibility(newCreatedPlaylistTab);
        newCreatedPlaylistTab.click();
        return this;
    }

    public boolean isSongInPlaylist(String songTitle) throws InterruptedException {
        try {
            waitForVisibility(tracklist);

            WebElement songTitleElement = tracklist.findElement(By.xpath("//div[@data-testid='playlist-tracklist']"));
            String title = songTitleElement.getText();

            return title.contains(songTitle);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isWhitneyHoustonTrackPresent(WebDriver driver) {
        WebElement trackElement = driver.findElement(By.xpath("//div[@data-testid=\"playlist-inline-curation-results\"]"));

        // Get the track details, e.g., track title, artist, etc.
        String trackDetails = trackElement.getText();

        // Check if the track by Whitney Houston is present (adjust the condition as per your specific track details).
        return trackDetails.contains("Whitney Houston");
    }

    public SpotifySearchPage deleteTrackFromPlaylist(){
        waitForVisibility(trackIdInList);
        trackIdInList.click();
        waitForVisibility(contextMenuButton);
        contextMenuButton.click();
        waitForVisibility(deleteOption);
        deleteOption.click();
        return this;
    }

}