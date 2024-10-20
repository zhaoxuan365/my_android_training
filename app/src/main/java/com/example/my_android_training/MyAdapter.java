package com.example.my_android_training;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<MyItem> items;
    private OnItemClickListener listener;

    public MyAdapter(List<MyItem> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyItem item = items.get(position);
        holder.imageView.setImageResource(item.getImageResId());
        holder.videoView.setVideoURI(item.getVideoUri());

        holder.videoView.setVisibility(View.GONE);
        holder.imageView.setVisibility(View.VISIBLE);

        holder.imageView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onImageClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        VideoView videoView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            videoView = itemView.findViewById(R.id.video_view);
        }
    }

    public interface OnItemClickListener {
        void onImageClick(int position);
    }

}
