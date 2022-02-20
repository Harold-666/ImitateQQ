package com.example.imitateqq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.imitateqq.fragment.ContactFragment;
import com.example.imitateqq.fragment.MeFragment;
import com.example.imitateqq.fragment.MsgFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;  // 底部导航栏

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 底部导航栏
        bottomNavigationView = findViewById(R.id.bottom_nav);

        // 首次进入主界面时，默认先显示个人资料页面
        if (savedInstanceState == null){
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MeFragment()).commit();
        }

        /**
         * 底部导航切换页面
         * */
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch(item.getItemId()){
                    case R.id.message:
                        fragment = new MsgFragment();
                        break;
                    case R.id.contact:
                        fragment = new ContactFragment();
                        break;
                    case R.id.person:
                        fragment = new MeFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                return true;
            }
        });
    }

    /**
     * 按返回键可以弹出用于提示用户是否要退出APP的对话框
     * */
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("提示").setMessage("确定要退出程序吗？")
                .setPositiveButton("确定", (dialog, which) -> {
                    finish();
                    dialog.dismiss();
                })
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss())
                .show();
    }
}