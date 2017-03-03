package com.cyberswift.weatherapp.model;

import java.io.Serializable;

/**
 * Created by User-129-pc on 10-01-2017.
 */
public class UserClass implements Serializable{

    public String userName;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean isRemember() {
        return isRemember;
    }

    public void setRemember(boolean remember) {
        isRemember = remember;
    }

    public boolean isAllDataFetched() {
        return isAllDataFetched;
    }

    public void setAllDataFetched(boolean allDataFetched) {
        isAllDataFetched = allDataFetched;
    }

    public  String userID;
    public String ID;

    public String password;

    public boolean isRemember= false;
    private boolean isAllDataFetched = false;

    public boolean getIsRemember() {
        return isRemember;
    }

    public void setIsRemember(boolean isRemember) {
        this.isRemember = isRemember;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean getIsAllDataFetched() {
        return isAllDataFetched;
    }

    public void setIsAllDataFetched(boolean isAllDataFetched) {
        this.isAllDataFetched = isAllDataFetched;
    }


}
