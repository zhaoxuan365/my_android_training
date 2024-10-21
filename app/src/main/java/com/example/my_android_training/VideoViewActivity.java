package com.example.my_android_training;


import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import androidx.annotation.Nullable;

public class VideoViewActivity extends Activity {

    private VideoView videoView;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_view);

        videoView = findViewById(R.id.video_view);
//        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.test);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                isPlaying = false; // 视频播放完成后重置状态
            }
        });

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    videoView.stopPlayback();
                } else {
                    videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.test);
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            videoView.start();
                        }
                    });
                }
                isPlaying = !isPlaying; // 切换播放状态
            }
        });
    }
}
