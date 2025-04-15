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

        Button calculateButton = findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(v -> {
            EditText sampleLength = findViewById(R.id.Sample_Length);
            EditText opticalResolution = findViewById(R.id.Optical_Resolution);
            EditText captureCardHeight = findViewById(R.id.Capture_Card_Height);
            TextView triggerTimes = findViewById(R.id.Trigger_times);

            try {
                double b11 = Double.parseDouble(sampleLength.getText().toString());
                double b12 = Double.parseDouble(opticalResolution.getText().toString());
                double b13 = Double.parseDouble(captureCardHeight.getText().toString());

                if (b12 != 0 && b13 != 0) {
                    double intermediate = (b11 + 100) * 1000 / b12 / b13;
                    double ceilingValue = Math.ceil(intermediate);
                    double result = ceilingValue * b13;

                    String resultText = "觸發次數: " + (int)result;
                    triggerTimes.setText(resultText);

                    Log.d("CALC_RESULT", "Result: " + result);
                } else {
                    triggerTimes.setText("錯誤：解析度或卡高不能為 0");
                    Log.e("CALC_ERROR", "B12 或 B13 不能為 0");
                }
            } catch (NumberFormatException e) {
                triggerTimes.setText("錯誤：請輸入有效數字");
                Log.e("CALC_ERROR", "請確認輸入為有效數字");
            }
        });

    }
}