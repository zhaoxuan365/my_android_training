package com.example.my_android_training.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.my_android_training.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomDialog2 extends Dialog {
    private static CustomDialog2 instance;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private TextView messageTextView;
    private boolean isLeftAligned;
    private boolean isShowing = false; // 标记是否正在显示

    // 私有构造函数
    private CustomDialog2(@NonNull Context context, boolean isLeftAligned) {
        super(context);
        this.isLeftAligned = isLeftAligned;
        initDialog();
    }

    // 单例方法
    public static CustomDialog2 getInstance(@NonNull Context context, boolean isLeftAligned) {
        if (instance == null) {
            synchronized (CustomDialog.class) {
                if (instance == null) {
                    instance = new CustomDialog2(context, isLeftAligned);
                }
            }
        }
        return instance;
    }

    private void initDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_layout);
        setCancelable(true);

        messageTextView = findViewById(R.id.tv_message);

        // 设置宽高和位置
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 设置整个 Dialog 背景为透明
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); // 移除背景的 dim 效果
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = 1000;
            params.height = 500;
            params.gravity = isLeftAligned ? Gravity.BOTTOM | Gravity.START : Gravity.BOTTOM | Gravity.END;

            // 设置边距
            int margin = (int) (16 * getContext().getResources().getDisplayMetrics().density); // dp 转 px
            params.x = margin;
            params.y = margin;

            window.setAttributes(params);
        }
    }

    @Override
    public void show() {
        if (isShowing) {
            Log.i("111","show, return, instance="+ instance.toString());
            return; // 如果已经显示，直接返回
        }
        isShowing = true; // 标记为显示状态
        super.show();
        Log.i("111","show, instance="+ instance.toString());
        // 在 show 时执行耗时操作
        executeBackgroundTask();
    }

    @Override
    public void dismiss() {
        if (!isShowing) {
            return; // 如果已经隐藏，直接返回
        }
        isShowing = false; // 重置显示状态
        super.dismiss();
        // 在 dismiss 时释放资源
        releaseResources();
        destroyInstance();
    }

    private void executeBackgroundTask() {
        executorService.submit(() -> {
            Log.i("111","executeBackgroundTask="+ instance.toString());
            // 模拟耗时操作
            try {
                Thread.sleep(2000); // 模拟网络请求
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String result = "Loaded Data"; // 模拟获取的数据

            // 更新UI
            mainHandler.post(() -> {
                if (messageTextView != null) {
                    messageTextView.setText(result);
                }
            });
        });
    }

    private void releaseResources() {
        // 释放资源的逻辑
        // 例如关闭网络连接、释放图片资源等
    }

    public void destroyInstance() {
        if (instance != null) {
            instance = null;
        }
    }
}


