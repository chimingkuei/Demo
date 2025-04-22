package com.example.demo;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.util.Log;


public class LineScan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_line_scan);

        //計算觸發次數
        Button calculateTriggerTimes = findViewById(R.id.Calculate_Trigger_Times);
        calculateTriggerTimes.setOnClickListener(v -> {
            EditText sampleLength = findViewById(R.id.Sample_Length);
            EditText opticalResolution = findViewById(R.id.Optical_Resolution);
            EditText captureCardHeight = findViewById(R.id.Capture_Card_Height);
            TextView triggerTimes = findViewById(R.id.Trigger_Times);
            Core.calculateTriggerTimes(sampleLength, opticalResolution, captureCardHeight, triggerTimes);
        });
        //計算移動最後位置
        Button calculateMoveLastPosition = findViewById(R.id.Calculate_Move_Last_Position);
        calculateMoveLastPosition.setOnClickListener(v -> {
            EditText opticalResolution = findViewById(R.id.Optical_Resolution);
            TextView triggerTimes = findViewById(R.id.Trigger_Times);
            EditText mobileResolution = findViewById(R.id.Mobile_Resolution);
            EditText triggerStartPosition = findViewById(R.id.Trigger_Start_Position);
            TextView resultText = findViewById(R.id.Move_Last_Position);
            Core.calculateMoveLastPosition(opticalResolution, triggerTimes, mobileResolution, triggerStartPosition, resultText);
        });
        //計算Line Rate
        Button calculateLineRate = findViewById(R.id.Calculate_Line_Rate);
        calculateLineRate.setOnClickListener(v -> {
            EditText captureCardPeriod = findViewById(R.id.Capture_Card_Period);
            TextView resultText = findViewById(R.id.Line_Rate);
            Core.calculateLineRate(captureCardPeriod, resultText);
        });
        //計算移動速度
        Button calculateMovementSpeed = findViewById(R.id.Calculate_Movement_Speed);
        calculateMovementSpeed.setOnClickListener(v -> {
            EditText opticalResolution = findViewById(R.id.Optical_Resolution);
            TextView lineRate = findViewById(R.id.Line_Rate);
            TextView resultText = findViewById(R.id.Movement_Speed);
            Core.calculateMovementSpeed(opticalResolution, lineRate, resultText);
        });

    }
}