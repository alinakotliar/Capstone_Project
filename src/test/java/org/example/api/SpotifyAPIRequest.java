package org.example.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.dto.CreatePlaylistRequest;
import org.example.dto.PlaylistBody;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.example.enums.SpotifyConstants.*;


public class SpotifyAPIRequest {


    public static Response createPlaylist(String accessToken, String userId, CreatePlaylistRequest playlistRequest) {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(playlistRequest)
                .post("/users/" + userId + "/playlists");
    }

    public static Response editPlaylist(String accessToken, String playlistId, PlaylistBody playlistBody) {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(playlistBody)
                .put("/playlists/" + playlistId);
    }

    public static String addTracksToPlaylist(String accessToken, String playlistId, String trackUri) {
        String apiUrl = BASE_URL + "/playlists/" + playlistId + "/tracks";

        List<String> uris = new ArrayList<>();
        uris.add(trackUri);

        Map<String, Object> trackRequest = new HashMap<>();
        trackRequest.put("uris", uris);
        trackRequest.put("position", 0);

        Response response = given()
                .baseUri(apiUrl)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(trackRequest)
                .post();

        response.then().statusCode(201);

        return response.jsonPath().getString("snapshot_id");
    }
    public static String removeTracksFromPlaylist(String accessToken, String playlistId, String snapshotId, String trackUri) {
        String apiUrl = BASE_URL + "/playlists/" + playlistId + "/tracks";

        List<Map<String, String>> tracks = new ArrayList<>();
        Map<String, String> track = new HashMap<>();
        track.put("uri", trackUri);
        tracks.add(track);

        Map<String, Object> removeTracksRequest = new HashMap<>();
        removeTracksRequest.put("tracks", tracks);
        removeTracksRequest.put("snapshot_id", snapshotId);

        Response response = given()
                .baseUri(apiUrl)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(removeTracksRequest)
                .delete();

        response.then().statusCode(200);

        return response.jsonPath().getString("snapshot_id");
    }

    public static String createPlaylistViaAPI(String accessToken, String userId, CreatePlaylistRequest playlistRequest) {
        Response response = createPlaylist(accessToken, userId, playlistRequest);

        if (response.getStatusCode() == 201) {
            // Playlist created successfully, extract and return the playlist ID
            return response.jsonPath().getString("id");
        } else {
            return null;
        }
    }

}
