package com.cyberswift.weatherapp.model;

import java.io.Serializable;

/**
 * Created by User-129-pc on 22-02-2017.
 */

public class Station implements Serializable {
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getRainStation() {
        return rainStation;
    }

    public void setRainStation(String rainStation) {
        this.rainStation = rainStation;
    }

    public String Id;
    public String rainStation;
}
