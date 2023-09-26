package com.hyeji.randomgame2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Arrays;

public class InputActivity extends AppCompatActivity {
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    ImageView button;
    Bundle bundle;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        intent = new Intent(InputActivity.this, GameActivity.class);
        bundle = new Bundle();

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] inputNumArray = {
                        Integer.parseInt(editText1.getText().toString()),
                        Integer.parseInt(editText2.getText().toString()),
                        Integer.parseInt(editText3.getText().toString()),
                        Integer.parseInt(editText4.getText().toString())
                };
                //System.out.println(Arrays.toString(inputNumArray));
                Log.d("InputActivity", "inputNumArray: " + Arrays.toString(inputNumArray));
                bundle.putIntArray("input_num_array", inputNumArray); // Pass the updated numArray as a result
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }
}
