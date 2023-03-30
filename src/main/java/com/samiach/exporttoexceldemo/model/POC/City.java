package com.samiach.exporttoexceldemo.model.POC;

import lombok.Data;

import java.util.List;

@Data
public class City {
    private String name;
    private List<Town> towns;

    public City(String name, List<Town> towns) {
        this.name = name;
        this.towns = towns;
    }
}
