package com.example.assignment2parta;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HandlePostBGTask implements Runnable {
    Activity uiAct;
    FragmentManager frag;
    int selectedUserId;

    public HandlePostBGTask(Activity uiAct, FragmentManager frag, int selectedUserId){
        this.uiAct = uiAct;
        this.frag = frag;
        this.selectedUserId = selectedUserId;
    }

    @Override
    public void run() {
        ExecutorService es = Executors.newSingleThreadExecutor();
        GetPostsTask postsTask = new GetPostsTask(uiAct, selectedUserId);
        Future<PostData> getPostResponse = es.submit(postsTask);
        PostData result = waitToGetPost(getPostResponse);
        Log.d("in run(): ", "helloo");

        if(result == null)
        {
            showToastMsg("Ops.. Task could not be performed.");
        }
        else{
            uiAct.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LoadPostFragment lpf = (LoadPostFragment) frag.findFragmentById(R.id.postSelector);
                    if(lpf == null)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("posts", result);
                        bundle.putInt("selectedUserId", selectedUserId);
                        Log.d("handle: ", "hello? we here?" + result.getPostList().get(0).getTitle());
                        lpf = new LoadPostFragment();
                        lpf.setArguments(bundle);
                        frag.beginTransaction().add(R.id.postSelector, lpf).commit();
                    }
                }
            });
        }
        es.shutdown();
    }

    private PostData waitToGetPost(Future<PostData> getPostResponse) {
        showToastMsg("Retrieving Posts");
        PostData getPosts = null;
        try
        {
            Log.d("wait to get post:", "we here");
            getPosts = getPostResponse.get(6000, TimeUnit.MILLISECONDS); //6 seconds
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

        return getPosts;
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
