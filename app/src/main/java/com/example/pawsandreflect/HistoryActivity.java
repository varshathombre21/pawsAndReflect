package com.example.pawsandreflect;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        TextView tvHistoryContent = findViewById(R.id.tv_history_content);
        Button btnBackHistory = findViewById(R.id.btn_back_history);

        // Fetch stored entries string
        SharedPreferences preferences = getSharedPreferences("JournalPrefs", MODE_PRIVATE);
        String allEntries = preferences.getString("all_entries", "");

        if (!allEntries.isEmpty()) {
            String[] entriesArray = allEntries.split("\\|\\|\\|");
            StringBuilder formattedHistory = new StringBuilder();
            for (String entry : entriesArray) {
                formattedHistory.append(entry).append("\n\n───────────────────\n\n");
            }
            tvHistoryContent.setText(formattedHistory.toString());
        }

        // Close this screen and slide back home when clicked!
        btnBackHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}