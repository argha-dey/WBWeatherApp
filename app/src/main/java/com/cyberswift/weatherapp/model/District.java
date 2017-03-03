package com.cyberswift.weatherapp.model;

import java.io.Serializable;

/**
 * Created by User-129-pc on 22-02-2017.
 */

public class District implements Serializable {
    public String DistrictName;
    public String DistrictID;

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public String getDistrictID() {
        return DistrictID;
    }

    public void setDistrictID(String districtID) {
        DistrictID = districtID;
    }
}
