package com.example.my_android_training;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private Context context;
    private String[] videoPaths; // 存储视频路径
    private VideoView currentlyPlayingVideoView;

    public VideoAdapter(Context context, String[] videoPaths) {
        this.context = context;
        this.videoPaths = videoPaths;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        holder.itemView.setOnClickListener(v -> {
            holder.videoView.setVideoURI(Uri.parse(videoPaths[position]));
            holder.videoView.setMediaController(new MediaController(holder.itemView.getContext()));
            holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.i("my","holder.videoView.isPlaying(), onPrepared");
                    if (holder.videoView.isPlaying()){
                        Log.i("my","holder.videoView.isPlaying() onPrepared, isPlaying = "+holder.videoView.isPlaying());
                        holder.videoView.stopPlayback();
                    }else{
                        holder.videoView.start();
                        Log.i("my","holder.videoView.isPlaying() onPrepared, else isPlaying = "+holder.videoView.isPlaying());
                    }
                }
            });
            holder.videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.i("my","holder.videoView.isPlaying(), onError");
                    return false;
                }
            });

//            if (currentlyPlayingVideoView != null && currentlyPlayingVideoView != holder.videoView
//            && currentlyPlayingVideoView.isPlaying()) {
//                currentlyPlayingVideoView.stopPlayback();
//            }
//            currentlyPlayingVideoView = holder.videoView;

        });

//        holder.itemView.setOnClickListener(v -> {
//            if (currentlyPlayingVideoView != null && currentlyPlayingVideoView != holder.videoView) {
//                currentlyPlayingVideoView.stopPlayback();
//                currentlyPlayingVideoView.setVisibility(View.GONE); // 隐藏已停止播放的视频
//            }
//            currentlyPlayingVideoView = holder.videoView;
//            holder.videoView.setVisibility(View.VISIBLE); // 显示当前视频视图
//            holder.videoView.start();
//        });
    }

    @Override
    public int getItemCount() {
        return videoPaths.length;
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.video_view);
        }

        public void releaseVideo() {
            if (videoView.isPlaying()) {
                videoView.stopPlayback();
            }
//            videoView.setVisibility(View.GONE); // 隐藏视频视图
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull VideoViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        holder.releaseVideo();
    }
}

