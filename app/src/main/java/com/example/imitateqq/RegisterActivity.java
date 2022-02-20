package com.example.imitateqq;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.imitateqq.uitls.MyHelper;
import com.example.imitateqq.uitls.SPSaveQQ;

public class RegisterActivity extends AppCompatActivity {
    private MyHelper helper;
    private SQLiteDatabase database;
    private String newAccount;          // 注册生成的账号
    private String password;            // 账号的密码
    private EditText nickname;          // 昵称
    private EditText rUserPsd;          // 注册密码
    private EditText rUserAgainPad;     // 再次确认密码
    private RadioGroup sexRadioGroup;   // 性别选择组
    private EditText rUsersignature;    // 个性签名

    String sex = "男";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        helper = new MyHelper(this, "user.db", 1);
        database = helper.getWritableDatabase();

        nickname = findViewById(R.id.et_user_name);             // 用户名
        rUserPsd = findViewById(R.id.et_pwd);                   // 密码填写
        rUserAgainPad = findViewById(R.id.et_pwd_again);        // 再次确认密码
        sexRadioGroup = findViewById(R.id.rg_sex);            // 性别选择
        rUsersignature = findViewById(R.id.et_signature); // 个性签名
        Button btn_register = findViewById(R.id.btn_register);  // 注册按钮

        sexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.registerRdBtnMale)
                    sex = "男";
                else
                    sex = "女";
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(); // 注册账号
            }
        });
    }

    /**
     * 注册账号
     * */
    private String register() {
        String first_psa = rUserPsd.getText().toString();
        String again_psa = rUserAgainPad.getText().toString();

        // 判断密码是否一致
        if (first_psa.equals(again_psa)){
            // 两次密码一致
            newAccount = random();  // 生成账号
            password = first_psa;   // 账号密码
            insert();               // 将账号密码及信息写入数据库
            saveQQ();               // 将新注册的账号密码保存到本地
            showExitDialog();       // 显示注册成功的提示框，用户确认后跳转回登录界面
            // Toast.makeText(Register.this,"注册成功,账号为:666666",Toast.LENGTH_SHORT).show();
            return password;
        } else {
            // 密码不一致，重新输入
            Toast.makeText(RegisterActivity.this,"两次密码不一致,重新输入",Toast.LENGTH_SHORT).show();
            rUserAgainPad.setText("");
        }
        return null;
    }

    /**
     * 生成随机账号
     * */
    public String random(){
        String strRand = "";
        // 随机生成9位数的账号
        for(int i = 0; i < 9; i++){
            strRand += String.valueOf((int)(Math.random() * 10)) ;
        }
        return strRand;
    }

    /**
     * 将注册信息写进数据库
     * */
    public void insert(){
        String name = nickname.getText().toString();
        String signature = rUsersignature.getText().toString().trim();

        if (signature.length() == 0){
            signature = "暂未填写个性签名！";
        }

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("account", newAccount);
        values.put("passwd", password);
        values.put("sex", sex);
        values.put("signature", signature);
        database.insert("userinfo", null, values);
        database.close();
        // debug
        Log.d("注册成功！", "name: " + name + "，account: " + newAccount + "，password: " + password + "，sex：" + sex + "，signature：" + signature);
    }

    /**
     * 保存注册的账号密码到 data.xml
     * */
    public void saveQQ(){
        boolean isSaveSuccess = SPSaveQQ.saveUserInfo(this, newAccount, password);
        if (!isSaveSuccess){
            Toast.makeText(this, "保存账号密码失败！", Toast.LENGTH_SHORT).show();
        } else {
            // debug
            Log.d("保存账号密码成功！", "账号：" + newAccount + "密码：" + password);
        }
    }

    /**
     * 生成账号提示框且返回登录界面
     * */
    public void showExitDialog(){
        new AlertDialog.Builder(this)
                .setTitle("注册成功")
                .setMessage("您的账号为:" + newAccount)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 跳转回登录页面，并销毁本activity
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra("account", newAccount);
                        intent.putExtra("password", rUserPsd.getText().toString());
                        startActivity(intent);
                        RegisterActivity.this.finish();
                    }
                })
                .show();
    }
}