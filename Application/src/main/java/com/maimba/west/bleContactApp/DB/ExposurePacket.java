package com.maimba.west.bleContactApp.DB;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "ExposurePackets_Table")
public class ExposurePacket {


    private @PrimaryKey(autoGenerate = true) long id;

    private String userData;
    private String userID;
    private String FirstName;
    private String LastName;
    private String userPhone;
    private String caseDisease;
    private String caseDateReported;

    public ExposurePacket(String userData, String userID, String FirstName,String LastName,String userPhone, String caseDisease, String caseDateReported) {
        this.userData = userData;
        this.userID = userID;
        this.FirstName = FirstName;
        this.LastName = LastName;
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

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
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
