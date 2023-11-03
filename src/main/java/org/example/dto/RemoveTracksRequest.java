package org.example.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class RemoveTracksRequest {
    private List<Map<String, String>> tracks;
    private String snapshot_id;

    public RemoveTracksRequest(List<Map<String, String>> tracks, String snapshot_id) {
        this.tracks = tracks;
        this.snapshot_id = snapshot_id;
    }
}
