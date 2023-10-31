package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter

public class CreatePlaylistResponse {
    private String description;
    private String id;
    private String name;
    private boolean isPublic;
    private boolean collaborative;


    public static class Owner {

        public String id;

    }

    public static class Root {
        public boolean collaborative;
        public String description;
        public String id;
        public String name;
        public Owner owner;

    }

}
