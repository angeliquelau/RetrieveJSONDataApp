package com.example.assignment2parta;

import android.os.Parcel;
import android.os.Parcelable;

public class Company implements Parcelable {
    private String username, cName, cCP, cBS;

    public Company(String username, String cName, String cCP, String cBS)
    {
        this.username = username;
        this.cName = cName; //company name
        this.cCP = cCP; //company's catch phrase
        this.cBS = cBS; //company's bs
    }

    protected Company(Parcel in) {
        username = in.readString();
        cName = in.readString();
        cCP = in.readString();
        cBS = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(cName);
        dest.writeString(cCP);
        dest.writeString(cBS);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    public String getUsername() { return username; }
    public String getCName() { return cName; }
    public String getCCP() { return cCP; }
    public String getCBS() { return cBS; }
}
