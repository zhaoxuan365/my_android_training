package com.example.my_android_training.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.my_android_training.R;

public class CustomDialogFragment extends DialogFragment {
    private static CustomDialogFragment instance;

    private static final int DEFAULT_WIDTH = 80;
    private static final int DEFAULT_HEIGHT = 40;

    private int width = DEFAULT_WIDTH;
    private int height = DEFAULT_HEIGHT;

    private CustomDialogFragment() {}

    public static synchronized CustomDialogFragment getInstance() {
        if (instance == null) {
            instance = new CustomDialogFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the custom layout
        return inflater.inflate(R.layout.dialog_custom, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            // Set dialog width and height
            dialog.getWindow().setLayout(width, height);
        }
    }

    public void showAtPosition(FragmentManager fragmentManager, int width, int height) {
        if (!isAdded()) {
            this.width = width > 0 ? width : DEFAULT_WIDTH;
            this.height = height > 0 ? height : DEFAULT_HEIGHT;

            show(fragmentManager, "CustomDialogFragment");
        }
    }

    public void dismissDialog() {
        if (isAdded()) {
            dismiss();
        }
    }

}

