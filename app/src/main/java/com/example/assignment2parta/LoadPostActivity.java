package com.example.assignment2parta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_post);


        Intent intent = getIntent();
        int selectedUserId = intent.getIntExtra("userId", 0);
        Log.d("Load post activity: ", "we here" + selectedUserId);
        ExecutorService es = Executors.newSingleThreadExecutor();
        // just doing usual fragment stuff here. just get the fragment and commit
        FragmentManager frag = getSupportFragmentManager();
        HandlePostBGTask handlePostBGTask = new HandlePostBGTask(LoadPostActivity.this, frag, selectedUserId);
        es.execute(handlePostBGTask);
    }
}