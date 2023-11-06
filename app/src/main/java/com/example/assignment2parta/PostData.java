package com.example.assignment2parta;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PostData implements Parcelable {
    private List<Post> postList;

    public PostData()
    {
        postList = new ArrayList<>();
    }

    protected PostData(Parcel p) {
        postList = p.readArrayList(List.class.getClassLoader());
    }

    public List<Post> getPostList() { return postList; }

    public void setPostList(JSONObject jPostsDetails) throws JSONException {
        postList.add( new Post(jPostsDetails.getInt("userId"), jPostsDetails.getInt("id"),
                jPostsDetails.getString("title"), jPostsDetails.getString("body")));

    }

    public static final Creator<PostData> CREATOR = new Creator<PostData>() {
        @Override
        public PostData createFromParcel(Parcel in) {
            return new PostData(in);
        }

        @Override
        public PostData[] newArray(int size) {
            return new PostData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(postList);
    }
}
