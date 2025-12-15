package com.edu.AAP_JGP_FPF.psp.ud4.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
    public String name;
    public String height;
    public String mass;

    @JsonProperty("hair_color")
    public String hairColor;

    @JsonProperty("skin_color")
    public String skinColor;

    @JsonProperty("eye_color")
    public String eyeColor;

    @JsonProperty("birth_year")
    public String birthYear;

    public String gender;
    public String url;

    // Necesitamos esto para saber qué naves pilota este personaje
    public List<String> starships;
    public List<String> vehicles;

    @Override
    public String toString() {
        return name;
    }
}
