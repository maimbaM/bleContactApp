package com.maimba.west.bleContactApp.DB;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ServiceData_Table")
public class ServiceData {
    private @PrimaryKey(autoGenerate = true)Long id;

    private String serviceData;

    public ServiceData(String serviceData) {
        this.serviceData = serviceData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceData() {
        return serviceData;
    }

    public void setServiceData(String serviceData) {
        this.serviceData = serviceData;
    }
}
