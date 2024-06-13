package com.example.personal_diary_2022551022;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class AuthorInfo extends AppCompatActivity {

    ImageButton rtnHomeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_info);

        rtnHomeBtn = findViewById(R.id.btn_rtnHome_author);
        rtnHomeBtn.setOnClickListener(v -> {
            finish();
        });
    }
}