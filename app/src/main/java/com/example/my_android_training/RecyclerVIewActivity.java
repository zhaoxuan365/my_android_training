package com.example.my_android_training;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerVIewActivity extends Activity implements MyAdapter.OnItemClickListener {

    private int[] clickCounts; // 存储每个条目的点击次数
    private MyAdapter adapter;
    RecyclerView recyclerView;
    private List<MyItem> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycler_view);

        recyclerView = findViewById(R.id.recycler_view);
        // 初始化数据和适配器
        adapter = new MyAdapter(items, this::onImageClick);
        recyclerView.setAdapter(adapter);

        clickCounts = new int[items.size()]; // 根据条目数量初始化数组
    }

    @Override
    public void onImageClick(int position) {
        clickCounts[position]++;
        if (clickCounts[position] == 3) {
            // 找到对应的 ViewHolder
            RecyclerView.ViewHolder viewHolder =
                    recyclerView.findViewHolderForAdapterPosition(position);
            if (viewHolder != null) {
                MyAdapter.ViewHolder myViewHolder = (MyAdapter.ViewHolder) viewHolder;
                myViewHolder.imageView.animate().alpha(0).setDuration(300).withEndAction(() -> {
                    myViewHolder.videoView.setVisibility(View.VISIBLE);
                    myViewHolder.videoView.setOnCompletionListener(mp -> {
                        myViewHolder.videoView.setVisibility(View.GONE);
                        myViewHolder.imageView.setVisibility(View.VISIBLE);
                        myViewHolder.imageView.animate().alpha(1).setDuration(300);
//                        myViewHolder.clickCount = 0; // 重置点击计数
                    });
                    myViewHolder.videoView.start();
                });
            }
        }
    }
}
