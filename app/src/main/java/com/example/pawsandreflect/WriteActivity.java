package com.example.pawsandreflect;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WriteActivity extends AppCompatActivity {

    private TextView tvDateHeader;
    private TextView tvSelectedMood;
    private EditText etJournalInput; // This is the class-level variable
    private Button btnSaveEntry;
    private Button btnBack;          // Added back button variable
    private ImageView ivMoodKitty;   // Added kitty image variable

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        // Hide top action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Connect our XML elements to Java
        tvDateHeader = findViewById(R.id.tv_date_header);
        tvSelectedMood = findViewById(R.id.tv_selected_mood);

        // FIX: Connect the class-level "etJournalInput" variable to the XML ID
        etJournalInput = findViewById(R.id.et_journal_entry);

        btnSaveEntry = findViewById(R.id.btn_save_entry);
        btnBack = findViewById(R.id.btn_back);              // Connect back button
        ivMoodKitty = findViewById(R.id.iv_mood_kitty);      // Connect kitty ImageView

        // 1. Get the mood sent from the first screen
        String selectedMood = getIntent().getStringExtra("selected_mood");
        if (selectedMood != null) {
            tvSelectedMood.setText("Feeling: " + selectedMood);
        }

        // 2. Set the correct mood kitty dynamically!
        if (selectedMood != null) {
            if (selectedMood.contains("Good")) {
                ivMoodKitty.setImageResource(R.drawable.happy_kitty);
            } else if (selectedMood.contains("Okay")) {
                ivMoodKitty.setImageResource(R.drawable.neutral_kitty);
            } else if (selectedMood.contains("Down")) {
                ivMoodKitty.setImageResource(R.drawable.sad_kitty);
            }
        }

        // 3. Automatically set today's real date (e.g., "July 14")
        // This will format it like "July 15, 10:42 PM"
        String currentDate = new SimpleDateFormat("MMMM d, h:mm a", Locale.getDefault()).format(new Date());
        tvDateHeader.setText(currentDate);

        // 4. Back button click listener (Closes screen and returns home)
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // This works just like the phone's back button!
            }
        });

        // 5. Save button click listener
        btnSaveEntry.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                // This now references the correctly connected etJournalInput!
                String userThoughts = etJournalInput.getText().toString().trim();

                if (userThoughts.isEmpty()) {
                    Toast.makeText(WriteActivity.this, "Please write something first! 🌸", Toast.LENGTH_SHORT).show();
                } else {
                    // Open SharedPreferences memory
                    android.content.SharedPreferences preferences = getSharedPreferences("JournalPrefs", MODE_PRIVATE);
                    String existingEntries = preferences.getString("all_entries", "");

                    // Format our new entry nicely (We'll format this with split separators soon!)
                    String newEntry = "📅 " + currentDate + " | " + selectedMood + "\n" + userThoughts;

                    // Append to old entries using "|||" as a separator separating them
                    if (!existingEntries.isEmpty()) {
                        existingEntries = newEntry + "|||" + existingEntries;
                    } else {
                        existingEntries = newEntry;
                    }

                    // Save the updated list back to phone memory
                    preferences.edit().putString("all_entries", existingEntries).apply();

                    Toast.makeText(WriteActivity.this, "Reflection saved! ✨", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}