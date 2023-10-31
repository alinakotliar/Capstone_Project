package org.example.dto;

import lombok.Getter;

@Getter
public class CreatePlaylistRequest {
        private String name;
        private String description;
        private boolean isPublic;
        private boolean isCollaborative;

        public CreatePlaylistRequest() {
            // Default constructor is required for JSON serialization/deserialization
        }

        public CreatePlaylistRequest(String name, String description, boolean isPublic) {
            this.name = name;
            this.description = description;
            this.isPublic = isPublic;
            this.isCollaborative = isCollaborative();
        }


    }

