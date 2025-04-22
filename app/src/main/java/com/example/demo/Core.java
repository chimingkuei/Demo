package com.example.demo;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class Core {
    public static void calculateTriggerTimes(EditText sampleLength, EditText opticalResolution, EditText captureCardHeight, TextView triggerTimes) {
        try {
            double b11 = Double.parseDouble(sampleLength.getText().toString());
            double b12 = Double.parseDouble(opticalResolution.getText().toString());
            double b13 = Double.parseDouble(captureCardHeight.getText().toString());
            if (b12 != 0 && b13 != 0) {
                double intermediate = (b11 + 100) * 1000 / b12 / b13;
                double ceilingValue = Math.ceil(intermediate);
                double result = ceilingValue * b13;
                String resultText = (int) result + "次";
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
    }

    public static void calculateMoveLastPosition(EditText opticalResolution, TextView triggerTimes, EditText mobileResolution, EditText triggerStartPosition, TextView resultText) {
        try {
            double b12 = Double.parseDouble(opticalResolution.getText().toString());
            double b14 = Double.parseDouble(triggerTimes.getText().toString().replace("次", "").trim());
            double b15 = Double.parseDouble(mobileResolution.getText().toString());
            double b16 = Double.parseDouble(triggerStartPosition.getText().toString());

            if (b15 != 0) {
                double result = b16 + b14 * b12 / b15;
                resultText.setText((int) result + "pluse");
                Log.d("FORMULA_RESULT", "Result: " + result);
            } else {
                resultText.setText("錯誤：B15 不能為 0");
                Log.e("FORMULA_ERROR", "除以 0 發生錯誤");
            }
        } catch (NumberFormatException e) {
            resultText.setText("錯誤：請輸入有效數字");
            Log.e("FORMULA_ERROR", "輸入格式錯誤", e);
        }
    }

    public static void calculateLineRate(EditText captureCardPeriod, TextView resultText) {
        try {
            double b18 = Double.parseDouble(captureCardPeriod.getText().toString());

            if (b18 != 0) {
                double result = 1000000 / b18;
                resultText.setText((int) result + "Hz");
                Log.d("FORMULA_RESULT", "Result: " + result);
            } else {
                resultText.setText("錯誤：B18 不能為 0");
                Log.e("FORMULA_ERROR", "除以 0 發生錯誤");
            }
        } catch (NumberFormatException e) {
            resultText.setText("錯誤：請輸入有效數字");
            Log.e("FORMULA_ERROR", "輸入格式錯誤", e);
        }
    }

    public static void calculateMovementSpeed(EditText opticalResolution, TextView lineRate, TextView resultText) {
        try {
            double b12 = Double.parseDouble(opticalResolution.getText().toString());
            double b19 = Double.parseDouble(lineRate.getText().toString().replace("Hz", "").trim());

            double result = b19 * b12;
            resultText.setText((int) result + "μm/s");
            Log.d("FORMULA_RESULT", "Result: " + result);
        } catch (NumberFormatException e) {
            resultText.setText("錯誤：請輸入有效數字");
            Log.e("FORMULA_ERROR", "輸入格式錯誤", e);
        }
    }
}