package com.cyberswift.weatherapp.model;

import java.io.Serializable;

/**
 * Created by User-129-pc on 23-02-2017.
 */

public class Trend implements Serializable {
    public String getTendId() {
        return tendId;
    }

    public void setTendId(String tendId) {
        this.tendId = tendId;
    }

    public String getTendName() {
        return tendName;
    }

    public void setTendName(String tendName) {
        this.tendName = tendName;
    }

    public String getTendShortForm() {
        return tendShortForm;
    }

    public void setTendShortForm(String tendShortForm) {
        this.tendShortForm = tendShortForm;
    }

    public  String tendId;
    public String tendName;
    public String tendShortForm;
}
