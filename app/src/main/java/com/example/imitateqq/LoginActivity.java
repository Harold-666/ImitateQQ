package com.example.imitateqq;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imitateqq.adapter.UserBeanAdapter;
import com.example.imitateqq.bean.UserBean;
import com.example.imitateqq.uitls.MyHelper;
import com.example.imitateqq.uitls.SPSaveQQ;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText mAccountView;
    private EditText mPasswordView;
    private ImageView mClearAccountView;
    private ImageView mClearPasswordView;
    private CheckBox mEyeView;
    private CheckBox mDropDownView;
    private Button mLoginView;
    private TextView mForgetPsdView;
    private TextView mRegisterView;
    private LinearLayout mTermsLayout;
    private TextView mTermsView;
    private RelativeLayout mPasswordLayout;

    private List<View> mDropDownInvisibleViews;
    private MyHelper helper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        helper = new MyHelper(this, "user.db", 1);
        database = helper.getWritableDatabase();

        initView();
        initDropDownGroup();
        readQQ();
    }

    /**
     * 初始化控件
     * */
    private void initView() {
        mAccountView = findViewById(R.id.et_input_account);             // 输入账号
        mPasswordView = findViewById(R.id.et_input_password);           // 输入密码
        mClearAccountView = findViewById(R.id.iv_clear_account);        // 清除账号框
        mClearPasswordView = findViewById(R.id.iv_clear_password);      // 清除密码框
        mEyeView = findViewById(R.id.iv_login_open_eye);                // 查看密码
        mDropDownView = findViewById(R.id.cb_login_drop_down);          // 下拉已保存账号选择
        mLoginView = findViewById(R.id.btn_login);                      // 登录按钮
        mForgetPsdView = findViewById(R.id.tv_forget_password);         // 忘记密码
        mRegisterView = findViewById(R.id.tv_register_account);         // 注册新用户
        mTermsLayout = findViewById(R.id.ll_terms_of_service_layout);   // 阅读已同意服务条款
        mTermsView = findViewById(R.id.tv_terms_of_service);            // 服务条款
        mPasswordLayout = findViewById(R.id.rl_password_layout);        // 密码框


        mPasswordView.setLetterSpacing(0.2f);

        mClearAccountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 清除账号输入框
                mAccountView.setText("");
            }
        });

        mClearPasswordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 清除密码输入框
                mPasswordView.setText("");
            }
        });

        mEyeView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 是否显示密码
                if(isChecked)
                    mPasswordView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                else
                    mPasswordView.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        mAccountView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // 当账号栏正在输入状态时，密码栏的清除按钮和眼睛按钮都隐藏
                if(hasFocus){
                    mClearPasswordView.setVisibility(View.INVISIBLE);
                    mEyeView.setVisibility(View.INVISIBLE);
                } else {
                    mClearPasswordView.setVisibility(View.VISIBLE);
                    mEyeView.setVisibility(View.VISIBLE);
                }
//                Log.i(TAG,"onFocusChange()::" + hasFocus);
            }
        });

        mPasswordView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // 当密码栏为正在输入状态时，账号栏的清除按钮隐藏
                if(hasFocus)
                    mClearAccountView.setVisibility(View.INVISIBLE);
                else
                    mClearAccountView.setVisibility(View.VISIBLE);
            }
        });

        mDropDownView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    // 下拉按钮点击的时候，密码栏、忘记密码、新用户注册、同意服务条款先全部隐藏
                    setDropDownVisible(View.INVISIBLE);
                    // 下拉箭头变为上拉箭头
                    // 弹出一个popupWindow
                    showDropDownWindow();
                } else {
                    setDropDownVisible(View.VISIBLE);
                }
            }
        });

        mDropDownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mForgetPsdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 忘记密码
                Toast.makeText(LoginActivity.this, "不给忘记密码！", Toast.LENGTH_SHORT).show();
            }
        });

        mRegisterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转注册页面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        mLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 登录
                login(mAccountView.getText().toString(), mPasswordView.getText().toString());
            }
        });

        mTermsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 进入服务条款界面
                Toast.makeText(LoginActivity.this, "开发者并没有撰写服务条款，仅供学习交流。", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化下拉列表
     * */
    private void initDropDownGroup() {
        mDropDownInvisibleViews = new ArrayList<>();
        mDropDownInvisibleViews.add(mPasswordView);
        mDropDownInvisibleViews.add(mForgetPsdView);
        mDropDownInvisibleViews.add(mRegisterView);
        mDropDownInvisibleViews.add(mPasswordLayout);
        mDropDownInvisibleViews.add(mLoginView);
        mDropDownInvisibleViews.add(mTermsLayout);
    }


    /**
     * 判断是否显示账号下拉已保存账号列表
     * */
    private void setDropDownVisible(int visible) {
        for (View view:mDropDownInvisibleViews){
            view.setVisibility(visible);
        }
    }

    /**
     * 账号输入框下拉已保存账号列表，静态显示，不存在这些账号
     * */
    private void showDropDownWindow() {
        final PopupWindow window = new PopupWindow(this);
        //下拉菜单里显示上次登录的账号，在这里先模拟获取有记录的用户列表
        List<UserBean> userBeanList = new ArrayList<>();
        userBeanList.add(new UserBean("12345678","123456789"));
        userBeanList.add(new UserBean("22669988","22669988"));
        //配置ListView的适配器
        final UserBeanAdapter adapter = new UserBeanAdapter(this);
        adapter.replaceData(userBeanList);
        //初始化ListView
        ListView userListView = (ListView) View.inflate(this, R.layout.window_account_drop_down,null);
        userListView.setAdapter(adapter);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //当下拉列表条目被点击时，显示刚才被隐藏视图,下拉箭头变上拉箭头
                //相当于mDropDownView取消选中
                mDropDownView.setChecked(false);
                //账号栏和密码栏文本更新
                UserBean checkedUser = adapter.getItem(position);
                mAccountView.setText(checkedUser.getAccount());
                mPasswordView.setText(checkedUser.getPassword());
                //关闭popupWindow
                window.dismiss();
            }
        });
        //添加一个看不见的FooterView，这样ListView就会自己在倒数第一个（FooterView）上边显示Divider，
        //进而在UI上实现最后一行也显示分割线的效果了
        userListView.addFooterView(new TextView(this));

        //配置popupWindow并显示
        window.setContentView(userListView);
        window.setAnimationStyle(0);
        window.setBackgroundDrawable(null);
        window.setWidth(mPasswordLayout.getWidth());
        window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setOutsideTouchable(true);
        window.showAsDropDown(mAccountView);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //当点击popupWindow之外区域导致window关闭时，显示刚才被隐藏视图，下拉箭头变上拉箭头
                //相当于mDropDownView取消选中
                mDropDownView.setChecked(false);
            }
        });
    }

    /**
     * 账号登录
     * */
    public void login(String mAccount, String mPassword){
        if (check(mAccount, mPassword)){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("account", mAccount);
            startActivity(intent);
            LoginActivity.this.finish();
        } else {
            Toast.makeText(LoginActivity.this, "请重新注册一个账号！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 检查账号密码的正确性
     * */
    private boolean check(String mAccount, String mPassword){
        Cursor cursor = database.query("userinfo", null, null, null, null, null, null);

        if (cursor.moveToNext()){
            if (!mAccount.equals("") && !mPassword.equals("")){
                while(cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    String account = cursor.getString(2);
                    String password = cursor.getString(3);
                    String sex = cursor.getString(4);
                    String signature = cursor.getString(5);
                    // debug
                    Log.d("====userinfo", "id: " + id + "，name: " + name + "，account: " + account + "，password: " + password + "，sex：" + sex + "，signature：" + signature);

                    if (!mAccount.equals(account)) {
                        Toast.makeText(LoginActivity.this, "账户不存在！", Toast.LENGTH_SHORT).show();
                    } else if (!mPassword.equals(password)) {
                        Toast.makeText(LoginActivity.this, "账号密码错误！请重新输入密码！", Toast.LENGTH_SHORT).show();
                    } else {
                        cursor.close();
                        return true;
                    }
                }
            } else {
                Toast.makeText(LoginActivity.this, "请输入账号密码！", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    /**
     * 获取已保存到本地data.xml的账号密码
     * */
    public void readQQ(){
        Map<String, String> userMap = SPSaveQQ.getUserInfo(this);

        String account = userMap.get("account");
        String password = userMap.get("password");
        // debug
        Log.d("本地账号获取:", "账号" + account + "密码" + password);

        if (account != null){
            // 如果获取到保存在本地的账号密码，自动填写至登录页面的输入框，用于新注册的账号登录方便
            mAccountView.setText(account);
            mPasswordView.setText(password);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDropDownInvisibleViews.clear();
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