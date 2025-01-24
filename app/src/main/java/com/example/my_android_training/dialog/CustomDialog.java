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

public class CustomDialog extends Dialog {
    private static CustomDialog instance;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private TextView messageTextView;
    private boolean isShowing = false; // 标记是否正在显示
    private boolean isLeftAligned = true; // 当前对齐方式，默认左下角

    // 私有构造函数
    private CustomDialog(@NonNull Context context) {
        super(context); // 使用透明背景主题
        initDialog();
    }

    // 单例方法
    public static CustomDialog getInstance(@NonNull Context context) {
        if (instance == null) {
            synchronized (CustomDialog.class) {
                if (instance == null) {
                    instance = new CustomDialog(context);
                }
            }
        }
        return instance;
    }

    // 初始化 Dialog
    private void initDialog() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_layout);
        setCancelable(false);

        messageTextView = findViewById(R.id.tv_message);

        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 设置背景透明
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); // 移除 dim 效果
        }
    }

    // 显示在左下角
    public void showLeft() {
        isLeftAligned = true;
        show();
    }

    // 显示在右下角
    public void showRight() {
        isLeftAligned = false;
        show();
    }

    @Override
    public void show() {
        if (isShowing) {
            Log.i("111","show, return, instance="+ instance.toString());
            return; // 如果已经显示，直接返回
        }
        isShowing = true; // 标记为显示状态
        Log.i("111","show, instance="+ instance.toString());
        // 设置窗口位置和大小
        configureWindowPosition();

        super.show();
        executeBackgroundTask(); // 执行耗时操作
    }

    @Override
    public void dismiss() {
        if (!isShowing) {
            Log.i("111", "dismiss, return");
            return; // 如果已经隐藏，直接返回
        }
        Log.i("111", "dismiss");
        isShowing = false; // 重置显示状态
        super.dismiss();
        releaseResources(); // 释放资源
        destroyInstance(); // 销毁单例
    }

    // 配置窗口的位置
    private void configureWindowPosition() {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            int widthPx = 900;
            int heightPx = 450;
            params.width = widthPx;
            params.height = heightPx;

            int margin = (int) (16 * getContext().getResources().getDisplayMetrics().density); // dp 转 px
            if (isLeftAligned) {
                params.gravity = Gravity.BOTTOM | Gravity.START;
            } else {
                params.gravity = Gravity.BOTTOM | Gravity.END;
            }
            params.x = margin;
            params.y = margin;

            window.setAttributes(params);
        }
    }

    private void executeBackgroundTask() {
        executorService.submit(() -> {
            try {
                Thread.sleep(2000); // 模拟耗时操作
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String result = "Loaded Data";

            mainHandler.post(() -> {
                if (messageTextView != null) {
                    messageTextView.setText(result);
                }
            });
        });
    }

    private void releaseResources() {
        // 释放资源逻辑
    }

    public void destroyInstance() {
        if (instance != null) {
            instance = null;
        }
    }
}
