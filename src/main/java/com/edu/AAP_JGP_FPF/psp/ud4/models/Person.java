package com.edu.AAP_JGP_FPF.psp.ud4.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
    public String name;
    public String url;

    // Necesitamos esto para saber qué naves pilota este personaje
    public List<String> starships;
    public List<String> vehicles;

    @Override
    public String toString() {
        return name;
    }
}
