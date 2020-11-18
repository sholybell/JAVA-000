package com.holybell.homework05.lesson09.q2;

import org.springframework.stereotype.Component;

@Component("lexusCar")
public class CarComponent {

    private String name="雷克萨斯";
    private String plateNo="闽D123456";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    @Override
    public String toString() {
        return "CarComponent{" +
                "name='" + name + '\'' +
                ", plateNo='" + plateNo + '\'' +
                '}';
    }
}
