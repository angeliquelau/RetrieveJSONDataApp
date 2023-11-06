package com.example.assignment2parta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExecutorService es = Executors.newSingleThreadExecutor();
        // just doing usual fragment stuff here. just get the fragment and commit
        FragmentManager frag = getSupportFragmentManager();
        HandleUserBGTask handleUserBGTask = new HandleUserBGTask(MainActivity.this, frag);
        es.execute(handleUserBGTask);
        es.shutdown();
    }
}