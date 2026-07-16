package com.example.pawsandreflect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnGood, btnNeutral, btnBad, btnViewHistory;
    private Button btnViewAnalytics; // Declared our new Analytics button!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Connect mood buttons and history button
        btnGood = findViewById(R.id.btn_good);
        btnNeutral = findViewById(R.id.btn_neutral);
        btnBad = findViewById(R.id.btn_bad);
        btnViewHistory = findViewById(R.id.btn_view_history);

        // Connect our brand-new Analytics button!
        btnViewAnalytics = findViewById(R.id.btn_view_analytics);

        // Click mood buttons -> Go to Writing Screen
        btnGood.setOnClickListener(v -> goToWritingScreen("🌸 Good"));
        btnNeutral.setOnClickListener(v -> goToWritingScreen("☁️ Okay"));
        btnBad.setOnClickListener(v -> goToWritingScreen("🌧️ Down"));

        // Click History Button -> Go to History Screen
        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        // Click Analytics Button -> Go to Analytics Screen!
        btnViewAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnalyticsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void goToWritingScreen(String mood) {
        Intent intent = new Intent(MainActivity.this, WriteActivity.class);
        intent.putExtra("selected_mood", mood);
        startActivity(intent);
    }
}