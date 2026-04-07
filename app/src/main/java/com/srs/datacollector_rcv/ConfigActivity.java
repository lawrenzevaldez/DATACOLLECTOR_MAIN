package com.srs.datacollector_rcv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfigActivity extends Activity {

    private EditText urlEditText;
    private Button saveButton, loadButton;
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_URL = "saved_url";

    private boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        urlEditText = (EditText) findViewById(R.id.urlEditText);
        saveButton = (Button) findViewById(R.id.saveButton);
        loadButton = (Button) findViewById(R.id.loadButton);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String savedUrl = sharedPreferences.getString(KEY_URL, "");
        urlEditText.setText(savedUrl);
        urlEditText.setEnabled(false);

        loadButton.setEnabled(!savedUrl.isEmpty());

        saveButton.setText("Edit URL");

        urlEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadButton.setEnabled(!s.toString().trim().isEmpty());
            }

            @Override public void afterTextChanged(Editable s) {}
        });

        saveButton.setOnClickListener(v -> {
            if(!isEditing) {
                isEditing = true;
                urlEditText.setEnabled(true);
                urlEditText.requestFocus();
                saveButton.setText("Save URL");
            } else {
                String url = urlEditText.getText().toString().trim();
                if (!url.isEmpty()) {
                    sharedPreferences.edit().putString(KEY_URL, url).apply();
                    Toast.makeText(ConfigActivity.this, "URL saved!", Toast.LENGTH_SHORT).show();
                    isEditing = false;
                    urlEditText.setEnabled(false);
                    saveButton.setText("Edit URL");
                    loadButton.setEnabled(true);
                } else {
                    Toast.makeText(ConfigActivity.this, "Please enter a URL", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadButton.setOnClickListener(v -> {
            startActivity(new Intent(ConfigActivity.this, MainActivity.class));
            finish();
        });
    }
}