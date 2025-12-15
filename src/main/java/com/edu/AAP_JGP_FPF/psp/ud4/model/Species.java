package com.edu.AAP_JGP_FPF.psp.ud4.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Species {
    public String name;
    public String classification;

    @JsonProperty("hair_colors")
    public String hairColors;

    public String url;

    @Override
    public String toString() { return name; }
}
