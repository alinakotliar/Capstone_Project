package org.example.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.dto.CreatePlaylistRequest;
import org.example.dto.CreatePlaylistResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class ApiTest {
    private static final String BASE_URL = "https://api.spotify.com/v1";
    private static final String ACCESS_TOKEN = "BQBTBjQd5wbiPOAAe-KY1gLckK5lG_vMnmfaYqA6_exrpPgs3xxPvnYRySuQI-aAv3zCZk5R1ziFi9bcmw05gvLe_qPfAwM8gsHhixU7NWgoZKyhOW4_NWBbNy-Hvn9KJ7aNoqRhm3oeWVSHGMyrfpsIRO057oTA0ZiXFVhuIhngq_aGfP_IsAkWcMApOwEgMC7_GCY5RHmVRrbunmUxxXVimbj5j6LFqRxIDo4CuNDOquoIJ7nqS-Avh--xbahvOVeMJpZOWve2pg";
    private static final String USER_ID = "31zs2rv33iekdx2jlcskxvl3adki"; // Replace with your actual user ID
    private String playlistId; // To store the playlist ID

    @BeforeMethod
    public void authSetUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test(priority = 1)
    public void testCreatePlaylist() {
        /* Create the request DTO
        CreatePlaylistRequest request = new CreatePlaylistRequest("New Playlist4", "New playlist description", false);

        // Send the POST request and receive the response
        Response response = given()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(request)
                .post("/users/{userId}/playlists", USER_ID);

        // Check the response status code
        int statusCode = response.getStatusCode();
        assertEquals(statusCode, 201);

        // Extract the response JSON and deserialize it into the DTO
        CreatePlaylistResponse responseBody = response.as(CreatePlaylistResponse.class);

        // Check the response DTO
        assertEquals("New Playlist2", responseBody.getName());
        assertEquals("New playlist description", responseBody.getDescription());
        //assertEquals(false, responseBody.isPublic());

        // You can also access other fields in the response DTO if needed
        // For example: responseBody.getId()

         */
        CreatePlaylistRequest request = new CreatePlaylistRequest("New Playlist", "New playlist description", false);

        // Send the POST request and receive the response
        Response response = given()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body(request)
                .post("/users/{userId}/playlists", USER_ID)
                .then()
                .statusCode(201)
                .extract()
                .response();

        // Extract the playlist ID from the response
        playlistId = response.jsonPath().get("id");
    }

    @Test(priority = 2)
    public void testUpdatePlaylist() {
        // Ensure that you have a playlist ID from the previous step
        Assert.assertNotNull(playlistId, "Playlist ID is not available");

        // Prepare the request payload for updating the playlist
        String updatedPlaylistName = "Updated Playlist";
        String updatedDescription = "Updated playlist description";
        boolean isPublic = false;

        // Send the PUT request to update the playlist
        given()
                .header("Authorization", "Bearer " + ACCESS_TOKEN)
                .contentType(ContentType.JSON)
                .body("{\"name\":\"" + updatedPlaylistName + "\",\"description\":\"" + updatedDescription + "\",\"public\":" + isPublic + "}")
                .put("/playlists/{playlistId}", playlistId)
                .then()
                .statusCode(200);
    }


    }
