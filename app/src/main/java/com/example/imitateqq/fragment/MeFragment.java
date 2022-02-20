package com.example.imitateqq.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.imitateqq.LoginActivity;
import com.example.imitateqq.R;
import com.example.imitateqq.uitls.MyHelper;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment {
    private View view;
    private RelativeLayout logout;
    private RelativeLayout changeAccount;
    private RelativeLayout closeApp;

    private MyHelper helper;
    private SQLiteDatabase database;
    private TextView nameView;
    private TextView qqView;
    private TextView sexView;
    private TextView signatureView;
    private String name;
    private String sex;
    private String signature;
    private String account;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_me, container, false);
        helper = new MyHelper(this.getContext(), "user.db", 1);
        database = helper.getReadableDatabase();

        Intent intent = getActivity().getIntent();
        account = intent.getStringExtra("account");

        readUserinfo(account);  // 获取账号信息
        initView(view);         // 初始化控件

        return view;
    }

    /**
     * 初始化控件
     * */
    private void initView(View view) {
        nameView = view.findViewById(R.id.tv_name);             // 用户名
        qqView = view.findViewById(R.id.tv_qq);                 // QQ账号
        sexView = view.findViewById(R.id.tv_sex);               // 性别
        signatureView = view.findViewById(R.id.tv_signature);   // 个性签名

        nameView.setText(name);
        qqView.setText(account);
        sexView.setText(sex);
        signatureView.setText(signature);

        logout = view.findViewById(R.id.rl_logout);
        changeAccount = view.findViewById(R.id.switch_account);
        closeApp = view.findViewById(R.id.close_app);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutTip();
            }
        });

        closeApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }

    /**
     * 查询账号信息
     * */
    private void readUserinfo(String account){
        Cursor cursor = database.query("userinfo", null, "account=?", new String[]{account},
                null, null, null);

        if (cursor.moveToNext()){
            do {
                int id = cursor.getInt(0);
                name = cursor.getString(1);
                String password = cursor.getString(3);
                sex = cursor.getString(4);
                signature = cursor.getString(5);
                // debug
                Log.d("userinfo", "id: " + id + "，name: " + name + "，account: " + account + "，password: " + password + "，sex：" + sex + "，signature：" + signature);

            } while(cursor.moveToNext());
        }
        cursor.close();
    }

    /**
     * 退出提示
     */
    private void logoutTip() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setMessage("你确定要退出登录吗？");
        dialog.setCancelable(false);
        dialog.setNeutralButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
