package com.example.my_android_training;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerVIewActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycler_view);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        String s = "android.resource://" + getPackageName() + "/" + R.raw.test;
        String[] videoPaths = {s, s};
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        VideoAdapter adapter = new VideoAdapter(this, videoPaths);
        recyclerView.setAdapter(adapter);
    }
}
