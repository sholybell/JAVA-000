package com.holybell.homework05.lesson09.q2;

public class Car {

    private String brand;   // 品牌
    private String plateNo; // 车牌号

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", plateNo='" + plateNo + '\'' +
                '}';
    }
}
