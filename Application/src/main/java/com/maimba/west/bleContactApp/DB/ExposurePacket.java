package com.maimba.west.bleContactApp.DB;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "ExposurePackets_Table")
public class ExposurePacket {


    private @PrimaryKey(autoGenerate = true) long id;

    private String userData;
    private String userID;
    private String userName;
    private String userPhone;
    private String caseDisease;
    private String caseDateReported;

    public ExposurePacket(String userData, String userID, String userName, String userPhone, String caseDisease, String caseDateReported) {
        this.userData = userData;
        this.userID = userID;
        this.userName = userName;
        this.userPhone = userPhone;
        this.caseDisease = caseDisease;
        this.caseDateReported = caseDateReported;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getCaseDisease() {
        return caseDisease;
    }

    public void setCaseDisease(String caseDisease) {
        this.caseDisease = caseDisease;
    }

    public String getCaseDateReported() {
        return caseDateReported;
    }

    public void setCaseDateReported(String caseDateReported) {
        this.caseDateReported = caseDateReported;
    }
}
