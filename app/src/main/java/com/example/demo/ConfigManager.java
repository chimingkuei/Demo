package com.example.demo;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ConfigManager {
    private static final String TAG = "ConfigManager";
    private static final String CONFIG_FILE_NAME = "config.json";
    private static ConfigManager instance;

    // === 參數 ===
    private int sampleLength;
    private int opticalResolution;
    private int captureCardHeight;
    private int mobileResolution;
    private int triggerStartPosition;
    private int captureCardPeriod;

    private ConfigManager() {}

    public static ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    /** 初始化設定（App 啟動時呼叫） */
    public void init(Context context) {
        try {
            File configFile = new File(context.getFilesDir(), CONFIG_FILE_NAME);
            String jsonText;

            if (configFile.exists()) {
                Log.d(TAG, "Loading config from internal storage: " + configFile.getAbsolutePath());
                jsonText = readFile(configFile);
            } else {
                Log.d(TAG, "Loading default config from assets");
                InputStream is = context.getAssets().open(CONFIG_FILE_NAME);
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                is.close();
                jsonText = new String(buffer, StandardCharsets.UTF_8);

                // 儲存預設設定到內部檔案
                saveToFile(context, jsonText);
            }

            // 解析 JSON
            JSONObject json = new JSONObject(jsonText);
            sampleLength = json.optInt("sampleLength", 0);
            opticalResolution = json.optInt("opticalResolution", 0);
            captureCardHeight = json.optInt("captureCardHeight", 0);
            mobileResolution = json.optInt("mobileResolution", 0);
            triggerStartPosition = json.optInt("triggerStartPosition", 0);
            captureCardPeriod = json.optInt("captureCardPeriod", 0);
        } catch (Exception e) {
            Log.e(TAG, "Error loading config", e);
        }
    }

    /** 儲存設定到內部檔案 */
    public void save(Context context) {
        try {
            JSONObject json = new JSONObject();
            json.put("sampleLength", sampleLength);
            json.put("opticalResolution", opticalResolution);
            json.put("captureCardHeight", captureCardHeight);
            json.put("mobileResolution", mobileResolution);
            json.put("triggerStartPosition", triggerStartPosition);
            json.put("captureCardPeriod", captureCardPeriod);

            saveToFile(context, json.toString());
            Log.d(TAG, "Config saved to internal storage.");
        } catch (Exception e) {
            Log.e(TAG, "Error saving config", e);
        }
    }

    private void saveToFile(Context context, String text) throws Exception {
        File file = new File(context.getFilesDir(), CONFIG_FILE_NAME);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(text.getBytes(StandardCharsets.UTF_8));
        fos.close();
    }

    private String readFile(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        fis.read(buffer);
        fis.close();
        return new String(buffer, StandardCharsets.UTF_8);
    }

    // === Getter / Setter ===
    public int getsampleLength(){ return sampleLength; }
    public int getopticalResolution(){ return opticalResolution; }
    public int getcaptureCardHeight(){ return captureCardHeight; }
    public int getmobileResolution() { return mobileResolution; }
    public int gettriggerStartPosition() { return triggerStartPosition; }
    public int getcaptureCardPeriod() { return captureCardPeriod; }

    public void setsampleLength(int value){ this.sampleLength = value;}
    public void setopticalResolution(int value){ this.opticalResolution = value; }
    public void setcaptureCardHeight(int value){ this.captureCardHeight = value; }
    public void setmobileResolution(int value) { this.mobileResolution = value; }
    public void settriggerStartPosition(int value) { this.triggerStartPosition = value; }
    public void setcaptureCardPeriod(int value) { this.captureCardPeriod = value; }
}
