package com.example.lab9_pnguye68;

import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.Manifest;

public class MainActivity extends AppCompatActivity {

    private final int SMS_PERMISSION_CODE = 100;
    private MyReceiver myReceiver = new MyReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkAndRequestPermissions();
    }

    private void checkAndRequestPermissions() {
        boolean smsGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                == PackageManager.PERMISSION_GRANTED;

        boolean notifGranted = Build.VERSION.SDK_INT < 33 || ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                == PackageManager.PERMISSION_GRANTED;

        if (!smsGranted || !notifGranted) {
            String[] permissions;
            if (Build.VERSION.SDK_INT >= 33) {
                permissions = new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.POST_NOTIFICATIONS};
            } else {
                permissions = new String[]{Manifest.permission.RECEIVE_SMS};
            }
            ActivityCompat.requestPermissions(this, permissions, SMS_PERMISSION_CODE);
        } else {
            registerReceiver(myReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
            Toast.makeText(this, "Broadcast Receiver Registered", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                registerReceiver(myReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied. App can't receive SMS.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}