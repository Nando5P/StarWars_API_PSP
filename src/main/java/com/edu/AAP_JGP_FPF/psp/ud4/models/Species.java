package com.edu.AAP_JGP_FPF.psp.ud4.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Species {
    public String name;
    public String classification;
    public String designation;

    @JsonProperty("average_height")
    public String averageHeight;

    @JsonProperty("skin_colors")
    public String skinColors;

    @JsonProperty("eye_colors")
    public String eyeColors;


    @JsonProperty("average_lifespan")
    public String averageLifespan;

    public String language;

    @JsonProperty("hair_colors")
    public String hairColors;

    public String url;

    @Override
    public String toString() { return name; }
}
