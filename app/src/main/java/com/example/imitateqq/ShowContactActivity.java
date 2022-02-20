package com.example.imitateqq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_contact_item);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String status = intent.getStringExtra("status");
        int icon = intent.getIntExtra("icon", 0);
        String signature = intent.getStringExtra("signature");

        TextView text = findViewById(R.id.name);
        TextView tStatus = findViewById(R.id.status);
        ImageView iv = findViewById(R.id.iv);
        TextView tSignature = findViewById(R.id.signature);

        iv.setImageResource(icon);
        text.setText(name);
        tStatus.setText("[" + status + "]");
        tSignature.setText("个性签名：" + signature);
    }
}