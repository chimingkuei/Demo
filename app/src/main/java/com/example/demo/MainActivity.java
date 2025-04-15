package com.example.demo;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button openWindows = findViewById(R.id.Open_Window);
        openWindows.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LineScan.class);
            startActivity(intent);
        });
    }
}