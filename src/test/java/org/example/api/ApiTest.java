package org.example.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.example.dto.CreatePlaylistRequest;
import org.example.dto.CreatePlaylistResponse;
import org.example.dto.PlaylistBody;
import org.example.dto.PlaylistTrackRequest;
import org.example.utils.EncodeLoginToken;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class ApiTest {
    private static final String BASE_URL = "https://api.spotify.com/v1";
    private static final String userId = "31zs2rv33iekdx2jlcskxvl3adki"; // Replace with your actual user ID
    private String playlistId; // To store the playlist ID
    private static String snapshotId; // To store the snapshot ID
    private static final String clientId = "3e2690d691404bb981047c5048fb3560";

    private static final String clientSecret = "8df5cd3087264a6bb0d8051ff08ac173";
    private static String accessToken = "BQA3DxPaE9AOCu_HS1c3xxDJO7-19J8yi0tkM5Ngvb0ZmuRvUmTTLlmDi7Sj6bkC-CfrzrKELaWfjbGBF2qOfTgns3sXYeOxMtYIlOtCNmFsighUsEtfPiEq74IRDfXIs_VtOn9VQULJ7z3h_2w9ICKfiO34FriGwu3dYxIc4nxJcKXk_LAmW165UfqQqvusl_GvboAo20tA_8mAGrxrPJOC0UDQh_27QWCJt4vpL0HzxbqiqnnOUnk8euL8zRlKtdi3bI34bQ5SEg";
    /*
    @BeforeClass
    public static void authenticationSpotify() {
        Response responseLogin =
                given()
                        .header("Authorization", "Basic " + EncodeLoginToken.getAuthToken(clientId, clientSecret))
                        .formParam("grant_type", "client_credentials")
                        .contentType("application/x-www-form-urlencoded")
                        .when()
                        .post("https://accounts.spotify.com/api/token");

        accessToken = responseLogin.jsonPath().get("access_token");

         Using EncodeLoginToken to convert clientId and clientSercret to base64
         */

    @BeforeMethod
    public void authSetUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testCreatePlaylist() {
        CreatePlaylistRequest playlistRequest = new CreatePlaylistRequest();
        playlistRequest.setName("New Playlist");
        playlistRequest.setDescription("New playlist description");
        playlistRequest.setPublic(false);

        Response createResponse = SpotifyAPIRequest.createPlaylist(accessToken, userId, playlistRequest);
        createResponse.then().statusCode(201);

        CreatePlaylistResponse playlistResponse = createResponse.as(CreatePlaylistResponse.class);
        playlistId = playlistResponse.getId();


        Assertions.assertThat(playlistResponse.getName()).isEqualTo("New Playlist");
        Assertions.assertThat(playlistResponse.getDescription()).isEqualTo("New playlist description");
        Assertions.assertThat(playlistResponse.isPublic()).isFalse();
    }

    @Test
    public void testEditPlaylist() {
        // Check if the playlistId is already set or create a new playlist
        if (playlistId == null) {
            // If playlistId is not set, create a playlist
            testCreatePlaylist();
        }

        // Verify if the playlist creation was successful
        if (playlistId != null) {
            PlaylistBody updatedPlaylist = new PlaylistBody();
            updatedPlaylist.setName("Updated Playlist");
            updatedPlaylist.setDescription("Updated playlist description");
            updatedPlaylist.setPublic(false);

            Response editResponse = SpotifyAPIRequest.editPlaylist(accessToken, playlistId, updatedPlaylist);
            editResponse.then().statusCode(200);

            // Additional checks for a successful edit
        } else {
            Assert.fail("Playlist creation failed, cannot perform the edit test.");
        }
    }
    @Test
    public void testAddTracksToPlaylist() {
        // Step 1: Create a new playlist
        CreatePlaylistRequest playlistRequest = new CreatePlaylistRequest();
        playlistRequest.setName("New Playlist");
        playlistRequest.setDescription("New playlist description");
        playlistRequest.setPublic(false);

        Response createResponse = SpotifyAPIRequest.createPlaylist(accessToken, userId, playlistRequest);
        createResponse.then().statusCode(201);

        CreatePlaylistResponse playlistResponse = createResponse.as(CreatePlaylistResponse.class);
        playlistId = playlistResponse.getId();

        // Add tracks to the newly created playlist
        String trackUri = "spotify:track:4yIfjMoivhXnY9lZkoVntq";
        String snapshotId = SpotifyAPIRequest.addTracksToPlaylist(accessToken, playlistId, trackUri);
    }

   @Test
    public void testRemoveSongFromPlaylist() {
       // Create a new playlist
       testCreatePlaylist();

       // Add tracks to the playlist
       String trackUri = "spotify:track:4yIfjMoivhXnY9lZkoVntq";
       snapshotId = SpotifyAPIRequest.addTracksToPlaylist(accessToken, playlistId, trackUri);
       Assertions.assertThat(snapshotId).isNotEmpty();

       // Remove tracks from the playlist
       SpotifyAPIRequest.removeTracksFromPlaylist(accessToken, playlistId, snapshotId, trackUri);
   }

}