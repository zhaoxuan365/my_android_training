package com.example.my_android_training.recyclerview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyOnItemTouchListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener onItemClickListener;
    private GestureDetector gestureDetector;
    private boolean isHandlingDoubleTap = false; // 标志位，标识是否在处理双击事件
    private Handler handler = new Handler(Looper.getMainLooper());
    // 延迟重置双击状态，确保不会因为点击的频率太快而多次触发双击
    private Runnable resetDoubleTap = new Runnable() {
        @Override
        public void run() {
            isHandlingDoubleTap = false; // 重置标志，允许处理单击事件
        }
    };

    // 延迟重置双击状态，确保在一定时间内只触发一个双击
    private void resetDoubleTapStateAfterDelay() {
        handler.postDelayed(resetDoubleTap, 500); // 500 毫秒后重置
    }

    private static final long DOUBLE_TAP_THRESHOLD = 300; // 双击时间阈值 (毫秒)
    private static final long MULTI_TAP_THRESHOLD = 500; // 多次点击时间阈值 (毫秒)

    private long lastTapTime = 0;  // 上次点击的时间
    private int tapCount = 0;      // 点击次数

    // 重置点击次数的操作
    private Runnable resetTapCount = new Runnable() {
        @Override
        public void run() {
            tapCount = 0;  // 重置点击次数
        }
    };

    public MyOnItemTouchListener(Context context, RecyclerView recyclerView,
                                 OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        gestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() {
//            @Override
//            public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
//                // 如果正在处理双击事件，则跳过单击事件
//                if (isHandlingDoubleTap) {
//                    return false; // 跳过单击事件的处理
//                }
//                View itemView = recyclerView.findChildViewUnder(e.getX(), e.getY());
//                if (onItemClickListener != null && itemView != null) {
//                    int position = recyclerView.getChildAdapterPosition(itemView);
//                    onItemClickListener.onItemSingleClick(itemView, position);
//                    return true;
//                }
//                return super.onSingleTapConfirmed(e);
//            }
//
//            @Override
//            public boolean onDoubleTap(@NonNull MotionEvent e) {
//                if (!isHandlingDoubleTap) {
//                    isHandlingDoubleTap = true; // 标记开始处理双击
//                    // 处理双击事件
//                    handler.removeCallbacks(resetDoubleTap); // 移除任何之前的重置操作
//                    // 在这里调用延迟重置
//                    resetDoubleTapStateAfterDelay();
//                    View itemView = recyclerView.findChildViewUnder(e.getX(), e.getY());
//                    if (onItemClickListener != null && itemView != null) {
//                        int position = recyclerView.getChildAdapterPosition(itemView);
//                        onItemClickListener.onItemDoubleClick(itemView, position);
//                        return true;
//                    }
//                }
//                return super.onDoubleTap(e);
//            }


            @Override
            public boolean onDown(@NonNull MotionEvent e) {
                long currentTapTime = System.currentTimeMillis();
                long timeDelta = currentTapTime - lastTapTime;
                Log.i("0105", "tapCount="+tapCount);
                // 判断点击间隔
                if (timeDelta <= DOUBLE_TAP_THRESHOLD) {
                    tapCount++; // 如果时间间隔小于阈值，增加点击次数
                } else {
                    tapCount = 1; // 重置点击次数为 1
                }

                lastTapTime = currentTapTime;  // 更新最后一次点击时间

                // 根据点击次数处理单击、双击或多次点击
                if (tapCount == 1) {
                    handler.removeCallbacks(resetTapCount); // 移除之前的重置操作
                    handler.postDelayed(resetTapCount, MULTI_TAP_THRESHOLD); // 延迟重置点击次数
                    View itemView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (onItemClickListener != null && itemView != null) {
                        int position = recyclerView.getChildAdapterPosition(itemView);
                        onItemClickListener.onItemSingleClick(itemView, position);
                        return true;
                    }
                } else if (tapCount >= 2) {
                    handler.removeCallbacks(resetTapCount); // 移除之前的重置操作
                    handler.postDelayed(resetTapCount, MULTI_TAP_THRESHOLD); // 延迟重置点击次数
                    View itemView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (onItemClickListener != null && itemView != null) {
                        int position = recyclerView.getChildAdapterPosition(itemView);
                        onItemClickListener.onItemDoubleClick(itemView, position);
                        return true;
                    }
                }
                return super.onDown(e);
            }
        });
    }

    public interface OnItemClickListener {
        void onItemSingleClick(View view, int position);

        void onItemDoubleClick(View view, int position);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        if (gestureDetector != null && gestureDetector.onTouchEvent(e)) {
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
