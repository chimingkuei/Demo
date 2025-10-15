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
        ConfigManager config = ConfigManager.getInstance();
        config.init(getApplicationContext());
        // 如果 res 有值（大於 0），就填入 EditText
        int res = config.getMobileResolution();
        EditText mobileResolution = findViewById(R.id.Mobile_Resolution);
        if (res > 0) {
            mobileResolution.setText(String.valueOf(res));
        }
        // 如果 trigger 有值（大於 0），就填入 EditText
        int trigger = config.getTriggerStartPosition();
        EditText triggerStartPosition = findViewById(R.id.Trigger_Start_Position);
        if (trigger > 0) {
            triggerStartPosition.setText(String.valueOf(trigger));
        }


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
            TextView resultText = findViewById(R.id.Move_Last_Position);
            // 執行計算
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
        //儲存參數
        Button saveConfig = findViewById(R.id.Save_Config);
        saveConfig.setOnClickListener(v -> {
            try {
                // 取得 EditText 的數值
                int newRes = Integer.parseInt(mobileResolution.getText().toString());
                int newTrigger = Integer.parseInt(triggerStartPosition.getText().toString());

                // 更新 ConfigManager
                config.setMobileResolution(newRes);
                config.setTriggerStartPosition(newTrigger);

                // 儲存到內部檔案
                config.save(getApplicationContext());

                // 使用 Log.d 顯示成功訊息
                Log.d("ConfigManager", "參數已儲存: MobileResolution=" + newRes + ", TriggerStartPosition=" + newTrigger);
            } catch (NumberFormatException e) {
                // 使用 Log.d 顯示錯誤訊息
                Log.d("ConfigManager", "輸入錯誤，請輸入正確的數字");
            }
        });

    }
}