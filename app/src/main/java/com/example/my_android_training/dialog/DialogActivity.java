package com.example.my_android_training.dialog;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.my_android_training.R;

public class DialogActivity extends AppCompatActivity {

    private static final String tag = "DialogActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(tag, "onCreate");

        setContentView(R.layout.activity_dialog);

        Button showLeftBottomDialog = findViewById(R.id.show_left_bottom_dialog);
        Button hideLeftBottomDialog = findViewById(R.id.hide_left_bottom_dialog);
        Button showRightBottomDialog = findViewById(R.id.show_right_bottom_dialog);
        Button hideRightBottomDialog = findViewById(R.id.hide_right_bottom_dialog);

        showLeftBottomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CustomDialogFragment.getInstance().showRight(getSupportFragmentManager());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        CustomDialogFragment.getInstance().dismiss();
                        CustomDialogFragment.getInstance().dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                CustomDialogFragment.getInstance().dismiss();
                            }
                        }, 1000L);
                    }
                }, 1000L);
            }
        });

        hideLeftBottomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog.getInstance(DialogActivity.this).dismiss();
            }
        });

        showRightBottomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog.getInstance(DialogActivity.this).showRight();
            }
        });

        hideRightBottomDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog.getInstance(DialogActivity.this).dismiss();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(tag, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(tag, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(tag, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(tag, "onStop");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.i(tag, "onWindowFocusChanged");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        CustomDialog dialog = CustomDialog.getInstance(this);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
