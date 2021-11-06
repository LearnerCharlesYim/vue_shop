package com.charles.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity(name ="tb_order")
public class Order extends BaseEntity {
    private String address;
    private Boolean isSend;
    private String number;
    private Integer price;
    private Boolean isPaid;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getSend() {
        return isSend;
    }

    public void setSend(Boolean send) {
        isSend = send;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }
}
