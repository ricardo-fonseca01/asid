package com.iStudent.model.entity;

import com.iStudent.model.entity.base.BasePersonEntity;
import javax.persistence.*;

@Entity
@Table(name = "parents")
public class Parent extends BasePersonEntity {

    @Column
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
