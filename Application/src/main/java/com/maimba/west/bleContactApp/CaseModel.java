package com.maimba.west.bleContactApp;

import java.util.Date;

public class CaseModel {

    private String Disease;
    private Date DateReported;

    public CaseModel() {
    }

    public String getDisease() {
        return Disease;
    }

    public void setDisease(String disease) {
        Disease = disease;
    }

    public Date getDateReported() {
        return DateReported;
    }

    public void setDateReported(Date dateReported) {
        DateReported = dateReported;
    }
}
