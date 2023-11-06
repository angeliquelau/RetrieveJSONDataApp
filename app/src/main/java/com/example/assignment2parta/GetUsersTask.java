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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

public class GetUsersTask implements Callable<UserData> {

    private String baseURL;
    private RemoteService rs;
    private Activity uiAct;

    public GetUsersTask(Activity uiAct)
    {
        baseURL = "https://jsonplaceholder.typicode.com";
        rs = RemoteService.getInstance(uiAct);
        this.uiAct = uiAct;
    }

    @Override
    public UserData call() throws Exception {
        UserData userData = null;
        String endpoint = baseURL + "/users";
        HttpURLConnection httpConn = rs.openConn(endpoint);
        if(httpConn == null)
        {
            uiAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(uiAct, "no user details found", Toast.LENGTH_LONG).show();
                }
            });
        }
        else
        {
            JSONArray jArr = getJson(endpoint);
            userData = getUserDetails(jArr);
            httpConn.disconnect();

            try {
                Thread.sleep(3000);
            } catch (Exception e) {
            }


        }

        return userData;
    }

    private JSONArray getJson(String endpoint) throws IOException, JSONException {
        URL url = new URL(endpoint);
        String jsonStr = IOUtils.toString(url, Charset.forName("UTF-8"));
        JSONArray jArr = new JSONArray(jsonStr);
        return jArr;
    }

    private UserData getUserDetails(JSONArray jArr) {
        //Log.d("getUsersTask: ", "endpoint: " + endpoint);

        UserData userData = new UserData();
        try
        {
            JSONArray jBase = jArr;


            for(int i = 0; i < jBase.length(); i++)
            {
                Log.d("json data: ", jBase.getJSONObject(i).toString());
                JSONObject jNames = jBase.getJSONObject(i);
                JSONObject jAddObj = jNames.getJSONObject("address");
                JSONObject jGeoObj = jAddObj.getJSONObject("geo");
                JSONObject jComObj = jNames.getJSONObject("company");

                userData.setUserList(jNames, i);
                userData.setAddList(jNames, jAddObj, i);
                userData.setGeoList(jNames, jGeoObj, i);
                userData.setCompanyList(jNames, jComObj, i);
                Log.d("user data: ", "size: " + userData.getUserList().size());
            }
        } catch (JSONException e) { //for JSONObject()
            e.printStackTrace();
        }
        return userData;
    }
}
