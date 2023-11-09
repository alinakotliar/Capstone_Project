package org.example.endtoend;

import io.restassured.response.Response;
import org.example.api.SpotifyAPIRequest;
import org.example.data.TestData;
import org.example.dto.CreatePlaylistRequest;
import org.example.dto.PlaylistBody;
import org.example.pajeobject.SpotifyHomePage;
import org.example.pajeobject.SpotifyLoginPage;
import org.example.pajeobject.SpotifySearchPage;
import org.example.pajeobject.SpotifyUserPage;
import org.example.ui.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
import static org.example.enums.SpotifyConstants.accessToken;
import static org.example.enums.SpotifyConstants.userId;

public class SpotifyEndToEndTests extends BaseTest {
    private SpotifyHomePage homePage;
    private SpotifyLoginPage loginPage;
    private SpotifySearchPage searchPage;
    private SpotifyUserPage userPage;
    @BeforeTest
    public void setUp() {
        setUpWebDriver();
        // Initialize page objects
        homePage = new SpotifyHomePage(webDriver);
        loginPage = new SpotifyLoginPage(webDriver);
        searchPage = new SpotifySearchPage(webDriver);
        userPage = new SpotifyUserPage(webDriver);

    }

    @Test
    public void testAddSongToPlaylist() throws InterruptedException {
        // Create a playlist via API
        String playlistId = createPlaylistViaAPI();

        // Perform UI steps to add a song to the playlist


        homePage.openLoginForm()
                .openProfileInfoModule();

        loginPage.open()
                .fillloginform(TestData.email, TestData.password)
                .signIn()
                .navigateToWebPlayer();
        searchPage
                .clickSearchIcon()
                .searchInput(TestData.searchQuery)
                .addTrackToPlaylist()
                .goToPlaylist();

        // Verify that the song was added to the playlist
        boolean isSongInPlaylist = searchPage.isSongInPlaylist(TestData.searchQuery);
        Assert.assertTrue(isSongInPlaylist, "Song not added to the playlist");
    }

    private String createPlaylistViaAPI() {
        CreatePlaylistRequest playlistRequest = new CreatePlaylistRequest();
        playlistRequest.setName("My new Playlist");
        playlistRequest.setDescription("New playlist description");
        playlistRequest.setPublic(false);

        return SpotifyAPIRequest.createPlaylistViaAPI(accessToken, userId, playlistRequest);
    }

    @Test
    public void testEditPlaylistDetails() {
        // Step 1: Create a new playlist and get the playlistId
        String playlistId = createPlaylistViaAPI();

        if (playlistId != null) {
            // Step 2: Send a PUT request to update the playlist details via the API
            PlaylistBody updatedPlaylist = new PlaylistBody();
            updatedPlaylist.setName("Updated Playlist");
            updatedPlaylist.setDescription("Updated playlist description");
            updatedPlaylist.setPublic(false);

            Response editResponse = SpotifyAPIRequest.editPlaylist(accessToken, playlistId, updatedPlaylist);
            editResponse.then().statusCode(200);

            // Step 3: Open Spotify website, log in, and check if the name of the playlist was updated
            homePage
                    .openLoginForm()
                    .openProfileInfoModule();
            loginPage
                    .open()
                    .fillloginform(TestData.email, TestData.password)
                    .signIn()
                    .navigateToWebPlayer();
            searchPage
                    .goToPlaylist();


            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            webDriver.navigate().refresh();
/*

            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(45));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h1[text()='Updated Playlist']")));

             */

            String actualPlaylistName = userPage.getPlaylistNameText();
            Assert.assertEquals(actualPlaylistName, "Updated Playlist");
        } else {
            Assert.fail("Playlist creation failed, cannot perform the edit test.");
        }
    }

    @AfterTest
    public void tearDown() {
        quit();
    }

}
