package com.rohan.rides.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Vehicle implements Parcelable {
    public static final Creator<Vehicle> CREATOR = new Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };
    private final String vin;
    private final String makeAndModel;
    private final String color;
    private final String carType;


    public Vehicle(String vin, String makeAndModel, String color, String carType) {
        this.vin = vin;
        this.makeAndModel = makeAndModel;
        this.color = color;
        this.carType = carType;
    }

    protected Vehicle(Parcel in) {
        vin = in.readString();
        makeAndModel = in.readString();
        color = in.readString();
        carType = in.readString();
    }

    public String getVin() {
        return vin;
    }

    public String getMakeAndModel() {
        return makeAndModel;
    }

    public String getColor() {
        return color;
    }

    public String getCarType() {
        return carType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vin);
        dest.writeString(makeAndModel);
        dest.writeString(color);
        dest.writeString(carType);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vin='" + vin + '\'' +
                ", makeAndModel='" + makeAndModel + '\'' +
                ", color='" + color + '\'' +
                ", carType='" + carType + '\'' +
                '}';
    }
}



