package com.example.assignment2parta;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class GetPostsTask implements Callable<PostData> {

    private String baseURL;
    private RemoteService rs;
    private Activity uiAct;
    private int selectedUserId;

    public GetPostsTask(Activity uiAct, int selectedUserId)
    {
        baseURL = "https://jsonplaceholder.typicode.com";
        rs = RemoteService.getInstance(uiAct);
        this.uiAct = uiAct;
        this.selectedUserId = selectedUserId;
    }

    @Override
    public PostData call() throws Exception {
        PostData postData = null;
        String endpoint = baseURL + "/posts";

        HttpURLConnection httpConn = rs.openConn(endpoint);
        if(httpConn == null)
        {
            uiAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(uiAct, "no posts found", Toast.LENGTH_LONG).show();
                }
            });
        }
        else
        {
            JSONArray jArr = getJson(endpoint);
            postData = getPosts(jArr);

            httpConn.disconnect();

            try {
                Thread.sleep(3000);
            } catch (Exception e) {
            }


        }

        return postData;
    }

    private JSONArray getJson(String endpoint) throws IOException, JSONException {
        URL url = new URL(endpoint);
        String jsonStr = IOUtils.toString(url, Charset.forName("UTF-8"));
        JSONArray jArr = new JSONArray(jsonStr);
        return jArr;
    }

    private PostData getPosts(JSONArray jArr) {
        PostData postData = new PostData();

        try
        {
            JSONArray jBase = jArr;

            for(int i = 0; i < jBase.length(); i++)
            {
                Log.d("get posts: ", "jBase " + jBase.getJSONObject(i).getInt("userId"));
                if(jBase.getJSONObject(i).getInt("userId") == selectedUserId)
                {
                    Log.d("post json data: ", jBase.getJSONObject(i).toString());
                    JSONObject jPosts = jBase.getJSONObject(i);
                    postData.setPostList(jPosts);
                }
            }
        } catch (JSONException e) { //for JSONObject()
            e.printStackTrace();
        }
        return postData;
    }
}
