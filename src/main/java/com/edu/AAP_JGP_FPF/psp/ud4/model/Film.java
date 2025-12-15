package com.edu.AAP_JGP_FPF.psp.ud4.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Film {
    public String title;

    @JsonProperty("episode_id")
    public int episodeId;

    public String url;

    // Listas de URLs que tendremos que "crawlear" después
    public List<String> characters;
    public List<String> planets;
    public List<String> species;
    public List<String> starships;
    public List<String> vehicles;

    @Override
    public String toString() {
        return title;
    }
}
