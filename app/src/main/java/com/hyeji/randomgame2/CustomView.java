package com.hyeji.randomgame2;


import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

class CustomView extends View {

    private static final int NUM_QUADRANTS = 4;
    private static final int QUADRANT_SIZE = 200;

    private int[] quadrantValues;
    private boolean[] quadrantTouched;
    private Paint paint;
    private boolean allQuadrantsTouched;
    Intent intent;
    Bundle bundle;
    private GameActivity gameActivity;
    private OnAllQuadrantsTouchedListener listener;
    private int[] numArray;


    public CustomView(Context context,int[] numArray) {
        super(context);
        this.numArray = numArray;
        quadrantValues = new int[NUM_QUADRANTS];
        quadrantTouched = new boolean[NUM_QUADRANTS];
        paint = new Paint();
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);
        allQuadrantsTouched = false;
        setBackgroundColor(Color.BLACK); // Set initial background color to black
        bundle = new Bundle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw quadrants
        int width = getWidth();
        int height = getHeight();
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        for (int i = 0; i < NUM_QUADRANTS; i++) {
            int left = (i % 2) * halfWidth;
            int top = (i / 2) * halfHeight;
            int right = left + halfWidth;
            int bottom = top + halfHeight;

            // Draw bold yellow lines to indicate quadrant boundaries
            paint.setColor(Color.YELLOW);
            paint.setStrokeWidth(10);
            canvas.drawLine(halfWidth, 0, halfWidth, height, paint);
            canvas.drawLine(0, halfHeight, width, halfHeight, paint);


            // Fill touched quadrant with randomly generated value
            if (quadrantTouched[i]) {
                if (quadrantValues[i] == 0) {
                    paint.setColor(Color.GREEN);
                    canvas.drawText("X", (left + right) / 2, (top + bottom) / 2, paint);
                } else if (quadrantValues[i] == 1) {
                    paint.setColor(Color.RED);
                    canvas.drawText("O", (left + right) / 2, (top + bottom) / 2, paint);
                }
            }
        }

        // 모든 사분면에 터치가 감지되었는지 확인
        if (allQuadrantsTouched && allQuadrantsTouched()) {
            float touchPercentage = getTouchPercentage();
            String message = "Percentage of 1s: " + touchPercentage + "%";
            //Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Get touch coordinates
            float touchX = event.getX();
            float touchY = event.getY();

            // Determine the touched quadrant
            int touchedQuadrant = getTouchedQuadrant(touchX, touchY);

            // Generate a random value for the touched quadrant only if it is not already touched
            if (!quadrantTouched[touchedQuadrant]) {
                Random random = new Random();
                int value = random.nextInt(2);
                quadrantValues[touchedQuadrant] = value;
                quadrantTouched[touchedQuadrant] = true;

                // Store the randomized value in numArray
                numArray[touchedQuadrant] = value;

                invalidate(); // Redraw the view to display the updated value
            } else {
                // Display toast for already touched quadrant
                Toast.makeText(getContext(), "이미 확인한 패입니다:(", Toast.LENGTH_SHORT).show();
            }
        }
        // Check if all quadrants are touched
        if (!allQuadrantsTouched && allQuadrantsTouched()) {
            allQuadrantsTouched = true;
            int touchValue = getTouch();
            //Toast.makeText(getContext(), "총 점수 :  " + touchValue, Toast.LENGTH_SHORT).show();

        }
        // Inside the onTouchEvent method, after checking if all quadrants are touched:
        if (allQuadrantsTouched() && onAllQuadrantsTouchedListener != null) {
            float winRate = getTouchPercentage();
            onAllQuadrantsTouchedListener.onAllQuadrantsTouched(winRate);
        }
        if (allQuadrantsTouched() && listener != null) {
            float winRate = getTouchPercentage();
            listener.onAllQuadrantsTouched(winRate);
        }



        return true;
    }
    private int getTouchedQuadrant(float touchX, float touchY) {
        int width = getWidth();
        int height = getHeight();
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        int quadrantX = (int) (touchX / halfWidth);
        int quadrantY = (int) (touchY / halfHeight);

        // Ensure quadrantX and quadrantY values are within valid range
        quadrantX = Math.max(0, Math.min(quadrantX, 1));
        quadrantY = Math.max(0, Math.min(quadrantY, 1));

        return quadrantY * 2 + quadrantX;
    }
    private boolean allQuadrantsTouched() {
        for (boolean touched : quadrantTouched) {
            if (!touched) {
                return false;
            }
        }
        return true;
    }
    public int getTouch() {
        int sum = 0;
        for (int value : quadrantValues) {
            sum += value;
        }
        return sum;
    }
    public boolean getAllQuadrantsTouched(){
        if (allQuadrantsTouched()){
            return true;
        }
        return false;
    }
    public float getTouchPercentage() {

        int sum = 0;
        int totalQuadrants = quadrantValues.length;

        for (int value : quadrantValues) {
            sum += value;
        }

        return ((float) sum / totalQuadrants) * 100;
    }
    // Setter method for the GameActivity reference
    public void setGameActivity(GameActivity activity) {
        gameActivity = activity;
    }
    // Define the interface
    public interface OnAllQuadrantsTouchedListener {
        void onAllQuadrantsTouched(float winRate);
    }
    public int[] getNumArray() {
        return numArray;
    }


    // Declare the listener variable
    private OnAllQuadrantsTouchedListener onAllQuadrantsTouchedListener;

    // Setter method for the listener
    public void setOnAllQuadrantsTouchedListener(OnAllQuadrantsTouchedListener listener) {
        this.listener = listener;
    }






}
