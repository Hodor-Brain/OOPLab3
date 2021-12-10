package com.example.lab3.custom_widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.example.lab3.R;

/**
 * Main view in application.
 */
public class StrokedTextView extends androidx.appcompat.widget.AppCompatButton {

    private static final int DEFAULT_STROKE_WIDTH = 0;

    private int _strokeColor;
    private float _strokeWidth;

    public StrokedTextView(Context context) {
        this(context, null, 0);
    }

    public StrokedTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StrokedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if(attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.StrokedTextAttrs);
            _strokeColor = a.getColor(R.styleable.StrokedTextAttrs_textStrokeColor,
                    getCurrentTextColor());
            _strokeWidth = a.getFloat(R.styleable.StrokedTextAttrs_textStrokeWidth,
                    DEFAULT_STROKE_WIDTH);

            a.recycle();
        }
        else {
            _strokeColor = getCurrentTextColor();
            _strokeWidth = DEFAULT_STROKE_WIDTH;
        }

        _strokeWidth = dpToPx(context, _strokeWidth);
    }

    public void setStrokeColor(int color) {
        _strokeColor = color;
    }

    public void setStrokeWidth(int width) {
        _strokeWidth = width;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(_strokeWidth > 0) {
            Paint p = getPaint();
            p.setStyle(Paint.Style.FILL);
            super.onDraw(canvas);
            int currentTextColor = getCurrentTextColor();
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(_strokeWidth);
            setTextColor(_strokeColor);
            super.onDraw(canvas);
            setTextColor(currentTextColor);
        } else {
            super.onDraw(canvas);
        }
    }

    /**
     * Converts density independent pixel(dp) value into device display specific pixel value.
     * @param context Context to access device specific display metrics
     * @param dp Density independent pixel value
     * @return Device specific pixel value.
     */
    public static int dpToPx(Context context, float dp)
    {
        final float scale= context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
