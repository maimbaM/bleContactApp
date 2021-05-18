package com.maimba.west.bleContactApp;

import java.util.Date;

public class CaseModel {

    private String caseDisease;
    private Date caseDateReported;

    public CaseModel() {
    }

    public CaseModel(String caseDisease, Date caseDateReported) {
        this.caseDisease = caseDisease;
        this.caseDateReported = caseDateReported;
    }

    public String getCaseDisease() {
        return caseDisease;
    }

    public void setCaseDisease(String caseDisease) {
        this.caseDisease = caseDisease;
    }

    public Date getCaseDateReported() {
        return caseDateReported;
    }

    public void setCaseDateReported(Date caseDateReported) {
        this.caseDateReported = caseDateReported;
    }
}
