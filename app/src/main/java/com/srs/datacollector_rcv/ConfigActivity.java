package com.srs.datacollector_rcv;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import com.srs.datacollector_rcv.BuildConfig;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.app.DownloadManager;
import android.app.AlertDialog;
import android.content.*;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.content.FileProvider;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConfigActivity extends Activity {
    private long downloadID;
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

        checkForUpdate();

        registerReceiver(onDownloadComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    // 🔍 CHECK UPDATE
    public void checkForUpdate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = getUpdateJson("http://192.168.0.91:8080/android/bo/update.json");

                try {
                    JSONObject obj = new JSONObject(json);

                    final int latestVersion = obj.getInt("versionCode");
                    final String apkUrl = obj.getString("apkUrl");
                    final boolean forceUpdate = obj.getBoolean("forceUpdate");

                    int currentVersion = BuildConfig.VERSION_CODE;

                    if (latestVersion > currentVersion) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showUpdateDialog(apkUrl, forceUpdate);
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 🌐 GET JSON FROM SERVER
    public String getUpdateJson(String urlString) {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            conn.setDefaultUseCaches(false);
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Pragma", "no-cache");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    // 💬 SHOW UPDATE DIALOG
    public void showUpdateDialog(final String apkUrl, boolean forceUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update Available");
        builder.setMessage("New version is available!");

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                downloadApk(apkUrl);
            }
        });

        if (!forceUpdate) {
            builder.setNegativeButton("Later", null);
        }

        builder.setCancelable(!forceUpdate);
        builder.show();
    }

    // ⬇️ DOWNLOAD APK
    public void downloadApk(String url) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        request.setTitle("Downloading Update");
        request.setDescription("Please wait...");
        request.setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
        );

        request.setDestinationInExternalFilesDir(
                this,
                Environment.DIRECTORY_DOWNLOADS,
                "update.apk"
        );

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        downloadID = manager.enqueue(request);
    }

    // 📥 INSTALL APK
    public void installApk(File file) {
        Uri uri = FileProvider.getUriForFile(
                this,
                getPackageName() + ".provider",
                file
        );

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(intent);
    }

    // 🔄 DOWNLOAD COMPLETE
    BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            if (downloadID == id) {

                DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadID);

                Cursor cursor = manager.query(query);

                if (cursor != null && cursor.moveToFirst()) {

                    int status = cursor.getInt(
                            cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                    );

                    if (status == DownloadManager.STATUS_SUCCESSFUL) {

                        String uriString = cursor.getString(
                                cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)
                        );

                        if (uriString != null) {
                            Uri uri = Uri.parse(uriString);

                            File file = new File(uri.getPath());

                            android.util.Log.d("UPDATE", "File size: " + file.length());

                            if (file.exists() && file.length() > 1000000) { // at least 1MB
                                installApk(file);
                            } else {
                                android.util.Log.e("UPDATE", "File corrupted or too small");
                            }
                        }

                    } else {
                        android.util.Log.e("UPDATE", "Download failed");
                    }

                    cursor.close();
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onDownloadComplete);
    }
}