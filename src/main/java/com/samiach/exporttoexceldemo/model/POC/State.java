package com.samiach.exporttoexceldemo.model.POC;

import lombok.Data;

import java.util.List;

@Data
public class State {
    private String name;
    private List<City> cities;

    public State(String name, List<City> cities) {
        this.name = name;
        this.cities = cities;
    }
}
