package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
