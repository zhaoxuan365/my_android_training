package com.example.my_android_training.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_android_training.R;

import java.util.Arrays;

public class RecyclerViewActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        MyAdapter myAdapter = new MyAdapter(Arrays.asList("111", "222"));
        recyclerView.addOnItemTouchListener(new MyOnItemTouchListener(this, recyclerView,
                new MyOnItemTouchListener.OnItemClickListener() {
            @Override
            public void onItemSingleClick(View view, int position) {
                Log.i("0105", "onItemSingleClick,position=" + position);
            }

            @Override
            public void onItemDoubleClick(View view, int position) {
                Log.i("0105", "onItemDoubleClick,position=" + position);
            }
        }));
        recyclerView.setAdapter(myAdapter);
    }
}
