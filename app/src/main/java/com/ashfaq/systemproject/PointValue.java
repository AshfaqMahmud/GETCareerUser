package com.ashfaq.systemproject;

public class PointValue {
    int xYear;
    Float yVal;

    public PointValue() {

    }

    public PointValue(int xYear, Float yVal) {
        this.xYear = xYear;
        this.yVal = yVal;
    }

    public int getxYear() {
        return xYear;
    }

    public void setxYear(int xYear) {
        this.xYear = xYear;
    }

    public Float getyVal() {
        return yVal;
    }

    public void setyVal(Float yVal) {
        this.yVal = yVal;
    }
}
