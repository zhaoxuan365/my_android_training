package com.example.my_android_training;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import androidx.cardview.widget.CardView;

public class CustomCardView extends CardView {
    private Paint borderPaint;
    private boolean borderEnabled = false; // 默认不显示边框
    private int borderColor = Color.BLACK; // 默认边框颜色
    private float borderWidth = 4; // 默认边框宽度
    private float borderRadius = 16;

    public CustomCardView(Context context) {
        super(context);
        init(null);
    }

    public CustomCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomCardView);
            borderEnabled = a.getBoolean(R.styleable.CustomCardView_borderEnabled, false);
            borderColor = a.getColor(R.styleable.CustomCardView_borderColor, Color.BLACK);
            borderWidth = a.getDimension(R.styleable.CustomCardView_borderWidth, 4);
            borderRadius = a.getDimension(R.styleable.CustomCardView_borderRadius, 4);
            a.recycle();
        }

        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setDither(true); // 允许抖动，以提高边缘质量
    }

    public void setBorderEnabled(boolean enabled) {
        borderEnabled = enabled;
        invalidate();
    }

    public void setBorderColor(int color) {
        borderColor = color;
        borderPaint.setColor(borderColor);
        invalidate();
    }

    public void setBorderWidth(float width) {
        borderWidth = width;
        borderPaint.setStrokeWidth(borderWidth);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (borderEnabled) {
            float halfBorderWidth = borderWidth / 2;
            RectF rectF = new RectF(halfBorderWidth, halfBorderWidth,
                    getWidth() - halfBorderWidth, getHeight() - halfBorderWidth);
            float cornerRadius = borderRadius; // 自定义圆角半径
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, borderPaint);
        }
    }
}
