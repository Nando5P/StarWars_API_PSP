package com.edu.AAP_JGP_FPF.psp.ud4.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Starship {
    public String name;
    public String model;
    public String manufacturer;

    @JsonProperty("cost_in_credits")
    public String costInCredits;

    public String length;

    @JsonProperty("max_atmosphering_speed")
    public String maxAtmospheringSpeed;

    public String crew;
    public String passengers;

    @JsonProperty("starship_class")
    public String starshipClass;

    @JsonProperty("hyperdrive_rating")
    public String hyperdriveRating;


    public String url;

    @Override
    public String toString() { return name; }
}
