package com.edu.AAP_JGP_FPF.psp.ud4.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Starship {
    public String name;
    public String model;
    public String url;

    @Override
    public String toString() { return name; }
}
