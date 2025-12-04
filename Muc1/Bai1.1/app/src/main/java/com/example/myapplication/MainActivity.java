package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "BackgroundPrefs";
    private static final String KEY_BACKGROUND_INDEX = "background_index";
    private static final int[] BACKGROUND_IMAGES = {
            R.drawable.bg_demo,
            R.drawable.bg_demo_2,
            R.drawable.bg_demo_3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.demo_image_view);
        
        // Thay đổi background mỗi lần load lại app
        changeBackground();
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Hàm thay đổi background mỗi lần load lại app
     * Sử dụng SharedPreferences để lưu trữ index của background hiện tại
     */
    private void changeBackground() {
        ConstraintLayout rootLayout = findViewById(R.id.rootLayout);
        if (rootLayout == null) {
            return;
        }

        // Lấy SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        
        // Lấy index hiện tại, mặc định là 0
        int currentIndex = prefs.getInt(KEY_BACKGROUND_INDEX, 0);
        
        // Tăng index lên 1, nếu vượt quá số lượng ảnh thì quay về 0
        int nextIndex = (currentIndex + 1) % BACKGROUND_IMAGES.length;
        
        // Áp dụng background mới
        rootLayout.setBackgroundResource(BACKGROUND_IMAGES[nextIndex]);
        
        // Lưu index mới vào SharedPreferences
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_BACKGROUND_INDEX, nextIndex);
        editor.apply();
    }
}