package com.cyberswift.weatherapp.model;

import java.io.Serializable;

/**
 * Created by User-129-pc on 23-02-2017.
 */

public class RainType implements Serializable {
    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String typeId;
    public String Type;
}
