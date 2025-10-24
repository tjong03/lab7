package com.example.androiduitesting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity that shows the chosen city name and a back button to return to MainActivity.
 */
public class ShowActivity extends AppCompatActivity {

    private TextView cityNameView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        cityNameView = findViewById(R.id.city_name);
        backButton = findViewById(R.id.back_button);

        // Read the city name passed from MainActivity
        String name = getIntent().getStringExtra(MainActivity.EXTRA_CITY_NAME);
        if (name == null) name = "";

        cityNameView.setText(name);

        // Back button finishes this activity (goes back to MainActivity)
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
