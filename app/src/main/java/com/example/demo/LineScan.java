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
        // 如果 sLen 有值（大於 0），就填入 EditText
        int sLen = config.getsampleLength();
        EditText sampleLength = findViewById(R.id.Sample_Length);
        if (sLen > 0) {
            sampleLength.setText(String.valueOf(sLen));
        }
        // 如果 optRes 有值（大於 0），就填入 EditText
        int optRes = config.getopticalResolution();
        EditText opticalResolution = findViewById(R.id.Optical_Resolution);
        if (optRes > 0) {
            opticalResolution.setText(String.valueOf(optRes));
        }
        // 如果 capCardH 有值（大於 0），就填入 EditText
        int capCardH = config.getcaptureCardHeight();
        EditText captureCardHeight = findViewById(R.id.Capture_Card_Height);
        if (capCardH > 0) {
            captureCardHeight.setText(String.valueOf(capCardH));
        }
        // 如果 mobRes 有值（大於 0），就填入 EditText
        int mobRes = config.getmobileResolution();
        EditText mobileResolution = findViewById(R.id.Mobile_Resolution);
        if (mobRes > 0) {
            mobileResolution.setText(String.valueOf(mobRes));
        }
        // 如果 trigStartPos 有值（大於 0），就填入 EditText
        int trigStartPos = config.gettriggerStartPosition();
        EditText triggerStartPosition = findViewById(R.id.Trigger_Start_Position);
        if (trigStartPos > 0) {
            triggerStartPosition.setText(String.valueOf(trigStartPos));
        }
        // 如果 capCardPeriod 有值（大於 0），就填入 EditText
        int capCardPeriod = config.getcaptureCardPeriod();
        EditText captureCardPeriod = findViewById(R.id.Capture_Card_Period);
        if (capCardPeriod > 0) {
            captureCardPeriod.setText(String.valueOf(capCardPeriod));
        }


        //計算觸發次數
        Button calculateTriggerTimes = findViewById(R.id.Calculate_Trigger_Times);
        calculateTriggerTimes.setOnClickListener(v -> {
            TextView triggerTimes = findViewById(R.id.Trigger_Times);
            Core.calculateTriggerTimes(sampleLength, opticalResolution, captureCardHeight, triggerTimes);
        });
        //計算移動最後位置
        Button calculateMoveLastPosition = findViewById(R.id.Calculate_Move_Last_Position);
        calculateMoveLastPosition.setOnClickListener(v -> {
            TextView triggerTimes = findViewById(R.id.Trigger_Times);
            TextView resultText = findViewById(R.id.Move_Last_Position);
            // 執行計算
            Core.calculateMoveLastPosition(opticalResolution, triggerTimes, mobileResolution, triggerStartPosition, resultText);
        });
        //計算Line Rate
        Button calculateLineRate = findViewById(R.id.Calculate_Line_Rate);
        calculateLineRate.setOnClickListener(v -> {
            TextView resultText = findViewById(R.id.Line_Rate);
            Core.calculateLineRate(captureCardPeriod, resultText);
        });
        //計算移動速度
        Button calculateMovementSpeed = findViewById(R.id.Calculate_Movement_Speed);
        calculateMovementSpeed.setOnClickListener(v -> {
            TextView lineRate = findViewById(R.id.Line_Rate);
            TextView resultText = findViewById(R.id.Movement_Speed);
            Core.calculateMovementSpeed(opticalResolution, lineRate, resultText);
        });
        //儲存參數
        Button saveConfig = findViewById(R.id.Save_Config);
        saveConfig.setOnClickListener(v -> {
            try {
                // 取得 EditText 的數值
                int newsLen = Integer.parseInt(sampleLength.getText().toString());
                int newoptRes = Integer.parseInt(opticalResolution.getText().toString());
                int newcapCardH = Integer.parseInt(captureCardHeight.getText().toString());
                int newmobRes = Integer.parseInt(mobileResolution.getText().toString());
                int newtrigStartPos = Integer.parseInt(triggerStartPosition.getText().toString());
                int newcapCardPeriod = Integer.parseInt(captureCardPeriod.getText().toString());
                // 更新 ConfigManager
                config.setsampleLength(newsLen);
                config.setopticalResolution(newoptRes);
                config.setcaptureCardHeight(newcapCardH);
                config.setmobileResolution(newmobRes);
                config.settriggerStartPosition(newtrigStartPos);
                config.setcaptureCardPeriod(newcapCardPeriod);
                // 儲存到內部檔案
                config.save(getApplicationContext());
            } catch (NumberFormatException e) {
                // 使用 Log.d 顯示錯誤訊息
                Log.d("ConfigManager", "輸入錯誤，請輸入正確的數字");
            }
        });

    }
}