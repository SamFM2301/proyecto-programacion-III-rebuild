package models;

import java.math.BigDecimal;

public class Business {
    private int id;
    private String name;
    private String address;
    private String phone;
    private String openingTime;
    private String closeTime;
    private BigDecimal qualification;

    public Business() {}

    public Business(int id, String name, String address, String phone, String openingTime, String closeTime, BigDecimal qualification) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.openingTime = openingTime;
        this.closeTime = closeTime;
        this.qualification = qualification;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public BigDecimal getQualification() {
        return qualification;
    }

    public void setQualification(BigDecimal qualification) {
        this.qualification = qualification;
    }
}