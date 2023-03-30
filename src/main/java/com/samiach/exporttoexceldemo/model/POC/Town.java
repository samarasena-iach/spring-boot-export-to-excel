package com.samiach.exporttoexceldemo.model.POC;

import lombok.Data;

@Data
public class Town {
    private String name;
    private Integer population;

    public Town(String name, Integer population) {
        this.name = name;
        this.population = population;
    }
}
