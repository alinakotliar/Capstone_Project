package org.example.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.example.dto.CreatePlaylistRequest;
import org.example.dto.CreatePlaylistResponse;
import org.example.dto.PlaylistBody;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.example.enums.SpotifyConstants;
import static org.example.enums.SpotifyConstants.*;


public class ApiTest {

    private String playlistId; // To store the playlist ID
    private static String snapshotId; // To store the snapshot ID


    @BeforeMethod
    public void authSetUp() {
        RestAssured.baseURI = SpotifyConstants.BASE_URL;
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
            // Create a new playlist
        CreatePlaylistRequest playlistRequest = new CreatePlaylistRequest();
        playlistRequest.setName("New Playlist");
        playlistRequest.setDescription("New playlist description");
        playlistRequest.setPublic(false);

        Response createResponse = SpotifyAPIRequest.createPlaylist(accessToken, userId, playlistRequest);
        createResponse.then().statusCode(201);

        CreatePlaylistResponse playlistResponse = createResponse.as(CreatePlaylistResponse.class);
        String playlistId = playlistResponse.getId();

        // Edit the playlist
        PlaylistBody updatedPlaylist = new PlaylistBody();
        updatedPlaylist.setName("Updated Playlist");
        updatedPlaylist.setDescription("Updated playlist description");
        updatedPlaylist.setPublic(false);

        Response editResponse = SpotifyAPIRequest.editPlaylist(accessToken, playlistId, updatedPlaylist);
        editResponse.then().statusCode(200);

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
        String snapshotId = SpotifyAPIRequest.addTracksToPlaylist(accessToken, playlistId, trackUri);
        Assert.assertNotNull(snapshotId);
    }

   @Test
    public void testRemoveSongFromPlaylist() {
       CreatePlaylistRequest playlistRequest = new CreatePlaylistRequest();
       playlistRequest.setName("New Playlist");
       playlistRequest.setDescription("New playlist description");
       playlistRequest.setPublic(false);

       Response createResponse = SpotifyAPIRequest.createPlaylist(accessToken, userId, playlistRequest);
       createResponse.then().statusCode(201);

       CreatePlaylistResponse playlistResponse = createResponse.as(CreatePlaylistResponse.class);
       String playlistId = playlistResponse.getId();

       // Add tracks to the playlist
       snapshotId = SpotifyAPIRequest.addTracksToPlaylist(accessToken, playlistId, trackUri);
       Assertions.assertThat(snapshotId).isNotEmpty();

       // Remove tracks from the playlist
       SpotifyAPIRequest.removeTracksFromPlaylist(accessToken, playlistId, snapshotId, trackUri);
   }
/*
    @AfterClass
    public void cleanup() {
        // Delete the playlist
        if (playlistId != null) {
            SpotifyAPIRequest.deletePlaylist(accessToken, playlistId);
        }
    }

 */
}