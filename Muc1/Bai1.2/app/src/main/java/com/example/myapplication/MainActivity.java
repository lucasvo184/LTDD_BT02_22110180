package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "BackgroundPrefs";
    private static final String KEY_SWITCH_STATE = "switch_state";
    private static final int BG_DEMO = R.drawable.bg_demo;
    private static final int BG_DEMO_2 = R.drawable.bg_demo_2;

    private ConstraintLayout rootLayout;
    private Switch backgroundSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.demo_image_view);
        
        rootLayout = findViewById(R.id.rootLayout);
        backgroundSwitch = findViewById(R.id.backgroundSwitch);
        
        // Load trạng thái switch và background đã lưu
        loadSavedState();
        
        // Thiết lập listener cho switch button
        backgroundSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Đổi background dựa trên trạng thái switch
            updateBackground(isChecked);
        });
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Hàm load trạng thái switch và background đã lưu từ SharedPreferences
     */
    private void loadSavedState() {
        if (rootLayout == null || backgroundSwitch == null) {
            return;
        }

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean switchState = prefs.getBoolean(KEY_SWITCH_STATE, false);
        
        // Khôi phục trạng thái switch
        backgroundSwitch.setChecked(switchState);
        
        // Áp dụng background tương ứng
        updateBackground(switchState);
    }

    /**
     * Hàm cập nhật background dựa trên trạng thái switch
     * Switch ON -> bg_demo_2, Switch OFF -> bg_demo
     */
    private void updateBackground(boolean switchOn) {
        if (rootLayout == null) {
            return;
        }

        // Chọn background dựa trên trạng thái switch
        int backgroundRes = switchOn ? BG_DEMO_2 : BG_DEMO;
        rootLayout.setBackgroundResource(backgroundRes);
        
        // Lưu trạng thái switch vào SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_SWITCH_STATE, switchOn);
        editor.apply();
    }
}