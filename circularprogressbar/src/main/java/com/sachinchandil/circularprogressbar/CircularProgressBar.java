package com.sachinchandil.circularprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Sachin Chandil on 07/04/2016.
 */
public class CircularProgressBar extends View {
    public CircularProgressBar(Context context) {
        super(context);
    }

    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CircularProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs)
    {
        TypedArray arr = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CircularProgressBar,0,0);

        try {
            showPercentText = arr.getBoolean(R.styleable.CircularProgressBar_showPercentText, false);
            finishedProgressColor = arr.getColor(R.styleable.CircularProgressBar_finishedProgressColor, Color.DKGRAY);
            unFinishedProgressColor = arr.getColor(R.styleable.CircularProgressBar_unFinishedProgressColor, Color.GRAY);
            percentTextColor = arr.getColor(R.styleable.CircularProgressBar_percentTextColor,Color.RED);
        }finally {
            arr.recycle();
        }
    }

    private int finishedProgressColor;
    private int unFinishedProgressColor;
    private boolean showPercentText;
    private int percentTextColor;


    private int startAngle = -90;
    private static final int SEPARATER = 2;
    private int circleRadius;
    private int borderRadius;
    private int pivotX, pivotY;
    private RectF circleBound;
    private int sweepAngle;
    private float percent;
    private String displayPercentText;


    private void initDrawValues() {
        int xPadding = getPaddingLeft() + getPaddingRight();
        int yPadding = getPaddingTop() + getPaddingBottom();
        int diameter = getWidth() > getHeight() ? getHeight() - yPadding : getWidth() - xPadding;
        pivotX = getPaddingLeft() + (getWidth() - xPadding) / 2;
        pivotY = getPaddingTop() + (getHeight() - yPadding) / 2;

        borderRadius = diameter / 2;
        circleRadius = (int) (borderRadius - dpToPx(SEPARATER));
        circleBound = new RectF(pivotX - circleRadius, pivotY - circleRadius, pivotX + circleRadius, pivotY + circleRadius);

        percent = (progress / max) * 100;
        sweepAngle = (int) (360 / (100 / percent));
    }
    private Paint paint;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initDrawValues();
        if(paint == null)
            paint = new Paint();
        canvas.drawColor(Color.TRANSPARENT);
        paint.setColor(unFinishedProgressColor);
        canvas.drawCircle(pivotX, pivotY, borderRadius, paint);

        paint.setColor(finishedProgressColor);
        canvas.drawArc(circleBound, startAngle, sweepAngle, true, paint);
        if(showPercentText)
            writeText(canvas);
    }

    private void writeText(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(percentTextColor);
        paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, (float) (circleRadius / 3.8), getResources().getDisplayMetrics()));
        displayPercentText = (int) percent + "%";
        float textWidth = paint.measureText(displayPercentText);
        Rect bound = new Rect();
        paint.getTextBounds(displayPercentText, 0, displayPercentText.length(), bound);
        float textXY[] = new float[]{pivotX - textWidth / 2, pivotY};
        canvas.drawText(displayPercentText, textXY[0], pivotY + (bound.bottom - bound.top) / 2, paint);
    }

    private float dpToPx(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private float progress = 110;
    private float max = 130;

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public int getFinishedProgressColor() {
        return finishedProgressColor;
    }

    public void setFinishedProgressColor(int finishedProgressColor) {
        this.finishedProgressColor = finishedProgressColor;
    }

    public boolean isShowPercentText() {
        return showPercentText;
    }

    public void setShowPercentText(boolean showPercentText) {
        this.showPercentText = showPercentText;
    }
}
