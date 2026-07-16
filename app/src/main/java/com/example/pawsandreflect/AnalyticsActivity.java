package com.example.pawsandreflect;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AnalyticsActivity extends AppCompatActivity {

    private TextView tvTotalReflections;
    private TextView tvGoodLabel, tvOkayLabel, tvDownLabel;
    private ProgressBar progressGood, progressOkay, progressDown;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Connect Views
        tvTotalReflections = findViewById(R.id.tv_total_reflections);
        tvGoodLabel = findViewById(R.id.tv_good_label);
        tvOkayLabel = findViewById(R.id.tv_okay_label);
        tvDownLabel = findViewById(R.id.tv_down_label);
        progressGood = findViewById(R.id.progress_good);
        progressOkay = findViewById(R.id.progress_okay);
        progressDown = findViewById(R.id.progress_down);
        btnBack = findViewById(R.id.btn_analytics_back);

        // Back button behavior
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Load and calculate analytics
        calculateMoodStats();
    }

    private void calculateMoodStats() {
        SharedPreferences preferences = getSharedPreferences("JournalPrefs", MODE_PRIVATE);
        String existingEntries = preferences.getString("all_entries", "");

        if (existingEntries.isEmpty()) {
            tvTotalReflections.setText("Total Reflections: 0");
            return; // No entries to calculate yet!
        }

        // Split database string into individual reflections
        String[] entries = existingEntries.split("\\|\\|\\|");
        int totalEntries = entries.length;

        int goodCount = 0;
        int okayCount = 0;
        int downCount = 0;

        // Loop through entries and count occurrences of each mood
        for (String entry : entries) {
            if (entry.contains("Good")) {
                goodCount++;
            } else if (entry.contains("Okay")) {
                okayCount++;
            } else if (entry.contains("Down")) {
                downCount++;
            }
        }

        // Calculate Percentages
        int goodPercent = (int) (((double) goodCount / totalEntries) * 100);
        int okayPercent = (int) (((double) okayCount / totalEntries) * 100);
        int downPercent = (int) (((double) downCount / totalEntries) * 100);

        // Update UI
        tvTotalReflections.setText("Total Reflections: " + totalEntries);

        tvGoodLabel.setText("🌸 Good Mood Days: " + goodPercent + "% (" + goodCount + ")");
        progressGood.setProgress(goodPercent);

        tvOkayLabel.setText("💜 Okay Mood Days: " + okayPercent + "% (" + okayCount + ")");
        progressOkay.setProgress(okayPercent);

        tvDownLabel.setText("☁️ Down Mood Days: " + downPercent + "% (" + downCount + ")");
        progressDown.setProgress(downPercent);
    }
}