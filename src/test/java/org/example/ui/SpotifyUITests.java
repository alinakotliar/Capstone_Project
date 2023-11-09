package org.example.ui;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.pajeobject.SpotifyHomePage;
import org.example.pajeobject.SpotifyLoginPage;
import org.example.pajeobject.SpotifySearchPage;
import org.example.pajeobject.SpotifyUserPage;
import org.example.ui.BaseTest;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SpotifyUITests extends BaseTest {
    private final SpotifyHomePage homePage = new SpotifyHomePage(webDriver);
    private final SpotifyLoginPage loginPage = new SpotifyLoginPage(webDriver);
    private final SpotifyUserPage userPage= new SpotifyUserPage(webDriver);
    private final SpotifySearchPage searchPage= new SpotifySearchPage(webDriver);


    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        webDriver.manage().window().maximize();
    }
/*
    @AfterMethod
    public void closeDriver() {
        webDriver.close();
        webDriver.quit();
    }

 */
    @DataProvider(name = "wrongCredentials")
    public Object[][] wrongCredentials() {
        return new Object[][] {
                {"qwerty", "alina"},
                {"akina", "qwerty"},
                {"qwerty", "qwerty"}
        };
    }
    @Test
    public void spotifyLoginWithEmptyCredentials() throws InterruptedException {
        homePage
                .openLoginForm()
                .openProfileInfoModule();
        loginPage
                .open()
                .fillloginform("123", "456")
                .clearUsername()
                .clearPassword()
                .signIn();
        Assert.assertTrue(loginPage.isUsernameErrorDisplayed(), "Username error message not displayed");
        Assert.assertTrue(loginPage.isPasswordErrorDisplayed(), "Password error message not displayed");

    }

    @Test(dataProvider = "wrongCredentials")
    public void spotifyLoginWithInorrectCredentials(String login, String password) {
        homePage
                .openLoginForm()
                .openProfileInfoModule();
        loginPage
                .open()
                .fillloginform(login, password)
                .signIn();
        String expectedErrorMessage = "Неправильное имя пользователя или пароль.";
        String actualErrorMessage = loginPage.getErrorMessageWrongCredentials();
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
    }

    @Test
    public void spotifyLoginWithCorrectCredentials() {
        homePage
                .openLoginForm()
                .openProfileInfoModule();
        String userInfoText = loginPage
                .open()
                .fillloginform("alina.kotliar.personal@gmail.com", "AccountforTest5!")
                .signIn()
                .navigateToWebPlayer()
                .logToProfile()
                .getUserInfoText();
        Assert.assertEquals(userInfoText, "Alina");

    }

    @Test
    public void testCreatePlaylist() {
        homePage
                .openLoginForm()
                .openProfileInfoModule();
        loginPage
                .open()
                .fillloginform("alina.kotliar.personal@gmail.com", "AccountforTest5!")
                .signIn()
                .navigateToWebPlayer()
                .createFirstPlaylist();
        String playlistNameFromListrow = userPage.getPlaylistNameFromListrow();
        String playlistNameFromMainMenu = userPage.getPlaylistNameFromMainMenu();

        Assert.assertEquals(playlistNameFromListrow, playlistNameFromMainMenu);

    }

    @Test
    public void testEditPlaylistDetail() {
        homePage
                .openLoginForm()
                .openProfileInfoModule();
        String actualPlaylistName = loginPage
                .open()
                .fillloginform("alina.kotliar.personal@gmail.com", "AccountforTest5!")
                .signIn()
                 .navigateToWebPlayer()
                 .clickCreatePlaylist()
                 .selectEditDetails("My favourite playlist")
                 .getPlaylistNameText();
        Assert.assertEquals(actualPlaylistName, "My favourite playlist");

    }
    @Test
    public void testSearchAndAddToPlaylist() throws InterruptedException {
        homePage
                .openLoginForm()
                .openProfileInfoModule();
        loginPage
                .open()
                .fillloginform("alina.kotliar.personal@gmail.com", "AccountforTest5!")
                .signIn()
                .navigateToWebPlayer()
                .clickCreatePlaylist();
        searchPage
                .clickSearchIcon()
                .searchInput("Whitney Elizabeth Houston")
                .addTrackToPlaylist()
                .goToPlaylist();
        boolean isSongInPlaylist = searchPage.isSongInPlaylist("Whitney Houston");
        Assert.assertTrue(isSongInPlaylist, "Song not added to playlist");

    }

    @Test
    public void testRemoveSongFromPlaylist() throws InterruptedException {
        homePage
                .openLoginForm()
                .openProfileInfoModule();
        loginPage
                .open()
                .fillloginform("alina.kotliar.personal@gmail.com", "AccountforTest5!")
                .signIn()
                .navigateToWebPlayer()
                .clickCreatePlaylist();
        searchPage
                .clickSearchIcon()
                .searchInput("Whitney Elizabeth Houston")
                .addSecondTrackToPlaylist()
                .goToPlaylist()
                .deleteTrackFromPlaylist();
        boolean isSongInPlaylist = searchPage.isSongInPlaylist("Whitney Houston");
        Assert.assertTrue(isSongInPlaylist, "Song not removed from playlist");
    }

    @Test
    public void testDeletePlaylist() throws InterruptedException {
            homePage
                    .openLoginForm()
                    .openProfileInfoModule();
            loginPage
                    .open()
                    .fillloginform("alina.kotliar.personal@gmail.com", "AccountforTest5!")
                    .signIn()
                    .navigateToWebPlayer()
                    .clickCreatePlaylist()
                    .deletePlaylist()
                    .isPlaylistDeleted();
        boolean isPlaylistDeleted = userPage.isPlaylistDeleted();
        Assert.assertTrue(isPlaylistDeleted);

    }



}
