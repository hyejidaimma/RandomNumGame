package com.hyeji.randomgame2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Result extends AppCompatActivity {
    TextView textViewScore;
    ImageView imageViewGameOver;
    ImageView imageViewNewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewScore = findViewById(R.id.textViewScore);
        imageViewGameOver = findViewById(R.id.imageViewGameOver);
        imageViewNewGame = findViewById(R.id.imageViewNewGame);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int[] numArray = bundle.getIntArray("num_array");
        int[] inputNumArray = bundle.getIntArray("input_num_array");

        if (numArray != null && inputNumArray != null) {
            float winRate = calculateWinRate(numArray, inputNumArray);
            String winRateText = String.format("%.2f%%", winRate);
            textViewScore.setText(winRateText);
        } else {
            textViewScore.setText("Error: Arrays are null");
        }

        imageViewGameOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Result.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imageViewNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Result.this, InputActivity.class);
                startActivity(intent2);
                finish();
            }
        });
    }

    private float calculateWinRate(int[] numArray, int[] inputNumArray) {
        if (numArray.length != inputNumArray.length) {
            return 0.0f; // Return 0 if the arrays have different lengths
        }

        int matchingCount = 0;
        int totalCount = numArray.length;

        for (int i = 0; i < numArray.length; i++) {
            if (numArray[i] == inputNumArray[i]) {
                matchingCount++;
            }
        }

        return (float) matchingCount / totalCount * 100;
    }
}
