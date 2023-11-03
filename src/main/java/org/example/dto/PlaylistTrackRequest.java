package org.example.dto;

import lombok.Data;

@Data
    public class PlaylistTrackRequest {
        private String uri;
        private int position;

        public PlaylistTrackRequest(String uri, int position) {
            this.uri = uri;
            this.position = position;}
}
