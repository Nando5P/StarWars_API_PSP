package com.edu.AAP_JGP_FPF.psp.ud4.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Planet {
    public String name;
    public String climate;
    public String terrain;
    public String diameter;
    public String population;

    @JsonProperty("rotation_period")
    public String rotationPeriod;

    @JsonProperty("orbital_period")
    public String orbitalPeriod;

    public String url;

    @Override
    public String toString() {
        return name;
    }
}
