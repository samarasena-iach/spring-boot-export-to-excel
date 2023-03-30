package com.samiach.exporttoexceldemo.model.POC;

import lombok.Data;

import java.util.List;

@Data
public class Country {
    private String name;
    private List<State> states;

    public Country(String name, List<State> states) {
        this.name = name;
        this.states = states;
    }
}
