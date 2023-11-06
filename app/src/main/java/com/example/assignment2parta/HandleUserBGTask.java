package com.example.assignment2parta;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HandleUserBGTask implements Runnable {
    Activity uiAct;
    FragmentManager frag;

    public HandleUserBGTask(Activity uiAct, FragmentManager frag){
        this.uiAct = uiAct;
        this.frag = frag;
    }

    @Override
    public void run() {
        ExecutorService es = Executors.newSingleThreadExecutor();
        GetUsersTask usersTask = new GetUsersTask(uiAct);
        Future<UserData> getUserResponse = es.submit(usersTask);
        UserData result = waitToGetUser(getUserResponse);

        if(result == null)
        {
            showToastMsg("Ops.. Task could not be performed.");
        }
        else{
            uiAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    UserFragment uf = (UserFragment) frag.findFragmentById(R.id.userSelector);
                    if(uf == null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("userData", result);
                        Log.d("handle: ", "hello? we here?" + result.getUserList().get(0).getUsername());
                        uf = new UserFragment();
                        uf.setArguments(bundle);
                        frag.beginTransaction().add(R.id.userSelector, uf).commit();
                    }
                }
            });
        }
        es.shutdown();
    }

    private UserData waitToGetUser(Future<UserData> getUserResponse) {
        showToastMsg("Retrieving User Details");
        UserData getUserData = null;
        try
        {
            getUserData = getUserResponse.get(6000, TimeUnit.MILLISECONDS); //6 seconds
        }
        //all these catch is for getUserResponse.get(...)
        catch (ExecutionException e) {
            e.printStackTrace();
            showToastMsg("Execution error");
        } catch (InterruptedException e) {
            e.printStackTrace();
            showToastMsg("Interrupted error");
        } catch (TimeoutException e) {
            e.printStackTrace();
            showToastMsg("timeout error");
        }

        return getUserData;
    }

    private void showToastMsg(String msg) {
        uiAct.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(uiAct, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
