package com.example.cio_machine_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class Error_Activity extends AppCompatActivity {
    TextView error_msg;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        error_msg = findViewById(R.id.error_act_msg);
        error_msg.setText(Static_class.error_no);

    }
}