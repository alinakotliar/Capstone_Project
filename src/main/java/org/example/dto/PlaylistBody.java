package org.example.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class PlaylistBody {
        private String name;
        private String description;
        private boolean isPublic;

    }

