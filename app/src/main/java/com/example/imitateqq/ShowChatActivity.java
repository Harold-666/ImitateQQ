package com.example.imitateqq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_chat_item);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int icon = intent.getIntExtra("icon", 0);
        String time = intent.getStringExtra("time");
        String message = intent.getStringExtra("message");

        TextView text = findViewById(R.id.name);
        ImageView iv = findViewById(R.id.iv);
        TextView tTime = findViewById(R.id.time);
        TextView tMessage = findViewById(R.id.message);

        iv.setImageResource(icon);
        text.setText(name);
        tTime.setText(time);
        tMessage.setText(message);
    }
}