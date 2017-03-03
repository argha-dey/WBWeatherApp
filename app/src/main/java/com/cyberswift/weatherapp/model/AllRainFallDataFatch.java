package com.cyberswift.weatherapp.model;

import java.io.Serializable;

/**
 * Created by User-129-pc on 23-02-2017.
 */

public class AllRainFallDataFatch implements Serializable {
    public String getRainGaugeId() {
        return rainGaugeId;
    }

    public void setRainGaugeId(String rainGaugeId) {
        this.rainGaugeId = rainGaugeId;
    }

    public String getAnualRainfall() {
        return anualRainfall;
    }

    public void setAnualRainfall(String anualRainfall) {
        this.anualRainfall = anualRainfall;
    }

    public String getJanCumRainfall() {
        return janCumRainfall;
    }

    public void setJanCumRainfall(String janCumRainfall) {
        this.janCumRainfall = janCumRainfall;
    }

    public String getJunCumRainfall() {
        return junCumRainfall;
    }

    public void setJunCumRainfall(String junCumRainfall) {
        this.junCumRainfall = junCumRainfall;
    }

    public String rainGaugeId;
    public String anualRainfall;
    public String janCumRainfall;
    public String junCumRainfall;
}
