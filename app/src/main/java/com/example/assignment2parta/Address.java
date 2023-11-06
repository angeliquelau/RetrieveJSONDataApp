package com.example.assignment2parta;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {
    private String username, street, suite, city, zipcode;


    public Address(String username, String street, String suite, String city, String zipcode)
    {
        this.username = username;
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
    }

    protected Address(Parcel in) {
        username = in.readString();
        street = in.readString();
        suite = in.readString();
        city = in.readString();
        zipcode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(street);
        dest.writeString(suite);
        dest.writeString(city);
        dest.writeString(zipcode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public String getUsername() { return username; }
    public String getStreet() { return street; }
    public String getSuite() { return suite; }
    public String getCity() { return city; }
    public String getZipcode() { return zipcode; }
}
