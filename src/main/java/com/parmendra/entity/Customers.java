package com.parmendra.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customers {

    @Id
    private Integer cid;
    private String name;
    private String address;
    private String mobileNo;
    private Integer orderId;

    public Customers() {
    }

    public Customers(Integer cid, String name, String address, String mobileNo, Integer orderId) {
        this.cid = cid;
        this.name = name;
        this.address = address;
        this.mobileNo = mobileNo;
        this.orderId = orderId;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
