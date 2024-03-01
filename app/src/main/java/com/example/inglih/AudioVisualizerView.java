package com.example.inglih;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class AudioVisualizerView extends View {

    private byte[] bytes;
    private float[] points;
    private Paint paint = new Paint();

    public AudioVisualizerView(Context context) {
        super(context);
        init();
    }

    public AudioVisualizerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AudioVisualizerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bytes = null;
        paint.setStrokeWidth(1f);
        paint.setColor(getResources().getColor(R.color.black));
    }

    public void updateVisualizer(byte[] bytes) {
        this.bytes = bytes;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bytes == null) {
            return;
        }
        if (points == null || points.length < bytes.length * 4) {
            points = new float[bytes.length * 4];
        }
        for (int i = 0; i < bytes.length - 1; i++) {
            points[i * 4] = getWidth() * i / (bytes.length - 1);
            points[i * 4 + 1] = getHeight() / 2 + ((byte) (bytes[i] + 128)) * (getHeight() / 2) / 128;
            points[i * 4 + 2] = getWidth() * (i + 1) / (bytes.length - 1);
            points[i * 4 + 3] = getHeight() / 2 + ((byte) (bytes[i + 1] + 128)) * (getHeight() / 2) / 128;
        }
        canvas.drawLines(points, paint);
    }
}