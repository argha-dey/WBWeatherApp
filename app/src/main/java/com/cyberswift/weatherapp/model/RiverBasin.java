package com.cyberswift.weatherapp.model;

import java.io.Serializable;

/**
 * Created by User-129-pc on 22-02-2017.
 */

public class RiverBasin implements Serializable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRiverBasin() {
        return riverBasin;
    }

    public void setRiverBasin(String riverBasin) {
        this.riverBasin = riverBasin;
    }

    public String id;
    public String riverBasin;
}
