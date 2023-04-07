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
    private final String kilometrage;


    public Vehicle(String vin, String makeAndModel, String color, String carType, String kilometrage) {
        this.vin = vin;
        this.makeAndModel = makeAndModel;
        this.color = color;
        this.carType = carType;
        this.kilometrage = kilometrage;
    }

    protected Vehicle(Parcel in) {
        vin = in.readString();
        makeAndModel = in.readString();
        color = in.readString();
        carType = in.readString();
        kilometrage = in.readString();
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

    public String getKilometrage() {
        return kilometrage;
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
        dest.writeString(kilometrage);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vin='" + vin + '\'' +
                ", makeAndModel='" + makeAndModel + '\'' +
                ", color='" + color + '\'' +
                ", carType='" + carType + '\'' +
                ", kilometrage='" + kilometrage + '\'' +
                '}';
    }
}



