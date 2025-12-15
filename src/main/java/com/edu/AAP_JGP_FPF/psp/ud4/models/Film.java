package com.edu.AAP_JGP_FPF.psp.ud4.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Film {
    public String title;

    @JsonProperty("episode_id")
    public int episodeId;

    @JsonProperty("opening_crawl")
    public String openingCrawl;

    public String director;

    public String producer;

    @JsonProperty("release_date")
    public String releaseDate;

    public String url;

    public List<String> characters;
    public List<String> planets;
    public List<String> species;
    public List<String> starships;
    public List<String> vehicles;

    @Override
    public String toString() { return title; }
}
