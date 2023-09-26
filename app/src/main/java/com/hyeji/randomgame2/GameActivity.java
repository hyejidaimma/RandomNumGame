package com.hyeji.randomgame2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Arrays;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements CustomView.OnAllQuadrantsTouchedListener {
    Intent intent;
    Bundle bundle;
    int[] numArray, inputNumArray;
    CustomView customView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        numArray = new int[4];
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            numArray[i] = random.nextInt(2);
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        inputNumArray = bundle.getIntArray("input_num_array");
        customView = new CustomView(this, numArray);
        setContentView(customView);
        customView.setOnAllQuadrantsTouchedListener(this);
    }

    @Override
    public void onAllQuadrantsTouched(float winRate) {
        // Handle the logic when all quadrants are touched
        int[] updatedNumArray = customView.getNumArray(); // Retrieve the updated numArray from CustomView
        System.out.println(Arrays.toString(updatedNumArray));
        bundle = new Bundle();
        intent = new Intent(GameActivity.this, Result.class);
        bundle.putFloat("win_rate", winRate);
        bundle.putIntArray("num_array", updatedNumArray); // Pass the updated numArray as a result
        bundle.putIntArray("input_num_array", inputNumArray); // Pass the updated numArray as a result
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

}
