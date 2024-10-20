package com.example.my_android_training;


import android.app.Activity;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.annotation.Nullable;

public class VideoViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_view);

        VideoView videoView = findViewById(R.id.video_view);
        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.test);
        videoView.start();
    }
}
