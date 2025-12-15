package com.edu.AAP_JGP_FPF.psp.ud4.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle {
    public String name;
    public String model;
    public String manufacturer;

    @JsonProperty("cost_in_credits")
    public String costInCredits;

    public String length;

    @JsonProperty("vehicle_class")
    public String vehicleClass;

    public String crew;
    public String passengers;

    public String url;

    @Override
    public String toString() { return name; }
}
