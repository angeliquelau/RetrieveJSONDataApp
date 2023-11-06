package com.example.assignment2parta;

import android.os.Parcel;
import android.os.Parcelable;

public class Geo implements Parcelable {
    private int latitude, longitude;
    private String username;

    public Geo(String username, int latitude, int longitude)
    {
        this.username = username;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Geo(Parcel in) {
        latitude = in.readInt();
        longitude = in.readInt();
        username = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(latitude);
        dest.writeInt(longitude);
        dest.writeString(username);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Geo> CREATOR = new Creator<Geo>() {
        @Override
        public Geo createFromParcel(Parcel in) {
            return new Geo(in);
        }

        @Override
        public Geo[] newArray(int size) {
            return new Geo[size];
        }
    };

    public String getUsername() { return username; }
    public int getLatitude() { return latitude; }
    public int getLongitude() { return longitude; }
}
