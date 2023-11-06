package com.example.assignment2parta;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/*referred to: https://medium.com/@royanimesh2211/implementing-the-parcelable-interface-in-android-b404819ca441
* for the parcelable related parts */
public class UserData implements Parcelable {
    private List<User> userList;
    private List<Address> addList;
    private List<Geo> geoList;
    private List<Company> companyList;

    public UserData()
    {
        userList = new ArrayList<>();
        addList = new ArrayList<>();
        geoList = new ArrayList<>();
        companyList = new ArrayList<>();
    }

    public UserData(Parcel p)
    {
        p.readList(userList, User.class.getClassLoader());
        p.readList(addList, Address.class.getClassLoader());
        p.readList(geoList, Geo.class.getClassLoader());
        p.readList(companyList, Company.class.getClassLoader());
    }

    public List<User> getUserList()
    {
        return userList;
    }

    public List<Address> getAddList()
    {
        return addList;
    }

    public List<Geo> getGeoList()
    {
        return geoList;
    }

    public List<Company> getCompanyList()
    {
        return companyList;
    }

    public void setUserList(JSONObject jNameDetails, int i) throws JSONException {
        userList.add(new User(jNameDetails.getInt("id"),
                jNameDetails.getString("name"),
                jNameDetails.getString("username"),
                jNameDetails.getString("email"),
                jNameDetails.getString("phone"),
                jNameDetails.getString("website")));
        Log.d("user data: ", "user list: " + userList.get(i).getUsername());
    }

    public void setAddList(JSONObject jNameDetails, JSONObject jAddObj, int i) throws JSONException {
        addList.add(new Address(jNameDetails.getString("username"),
                jAddObj.getString("street"),
                jAddObj.getString("suite"),
                jAddObj.getString("city"),
                jAddObj.getString("zipcode")));
        Log.d("user data: ", "add list: " + addList.get(i).getCity());
    }

    public void setGeoList(JSONObject jNameDetails, JSONObject jGeoObj, int i) throws JSONException {
        geoList.add(new Geo(jNameDetails.getString("username"),
                jGeoObj.getInt("lat"), jGeoObj.getInt("lng")));
        Log.d("user data: ", "geo list: " + geoList.get(i).getLatitude());
    }

    public void setCompanyList(JSONObject jNameDetails, JSONObject jComObj, int i) throws JSONException {
        companyList.add(new Company(jNameDetails.getString("username"),
                jComObj.getString("name"),
                jComObj.getString("catchPhrase"),
                jComObj.getString("bs")));
        Log.d("user data: ", "company list: " + companyList.get(i).getCName());
    }

    public List<String> getDetailByUN(String username)
    {
        Boolean found = false;
        int i = 0;
        List<String> userInfo = new ArrayList<>();
        while(found == false)
        {
            if(userList.get(i).getUsername().equals(username) && addList.get(i).getUsername().equals(username)
                    && geoList.get(i).getUsername().equals(username) && companyList.get(i).getUsername().equals(username))
            {
                userInfo.add(userList.get(i).getName());
                userInfo.add(userList.get(i).getUsername());
                userInfo.add(userList.get(i).getEmail());
                userInfo.add(userList.get(i).getPhone());
                userInfo.add(userList.get(i).getWebsite());
                userInfo.add(companyList.get(i).getCName());
                userInfo.add(companyList.get(i).getCCP());
                userInfo.add(companyList.get(i).getCBS());
                userInfo.add(addList.get(i).getStreet());
                userInfo.add(addList.get(i).getSuite());
                userInfo.add(addList.get(i).getCity());
                userInfo.add(addList.get(i).getZipcode());
                userInfo.add(String.valueOf(geoList.get(i).getLatitude()));
                userInfo.add(String.valueOf(geoList.get(i).getLongitude()));
                found = true;
            }
            else
            {
                i++;
                found = false;
            }
        }

        return userInfo;
    }

    public int getIdByUN(String username)
    {
        int selectedId = 0;
        Boolean found = false;
        int i = 0;
        while(found == false) {
            if(userList.get(i).getUsername().equals(username))
            {
                selectedId = userList.get(i).getId();
                found = true;
            }
            else
            {
                i++;
                found = false;
            }
        }
        return selectedId;
    }

    //for parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    //want to pass these objects to elsewhere
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Log.d("write: ", "u here again?? ");
        parcel.writeList(userList);
        parcel.writeList(addList);
        parcel.writeList(geoList);
        parcel.writeList(companyList);
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>(){

        @Override
        public UserData createFromParcel(Parcel parcel) {
            return new UserData(parcel);
        }

        @Override
        public UserData[] newArray(int i) {
            return new UserData[i];
        }
    };
}
