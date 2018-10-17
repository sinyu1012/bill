package com.wecanstudio.xdsjs.save.View.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.wecanstudio.xdsjs.save.Model.cache.SPModel;
import com.wecanstudio.xdsjs.save.Model.cache.SPUtils;
import com.wecanstudio.xdsjs.save.Model.config.Global;
import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.Utils.AppUtils;

public class RegistActivity extends BaseActivity {

    private EditText etAccount, etPwd, etPwdAgain;
    private String account, pwd, pwdAgain;
    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.onActivityCreateSetTheme(this, SPModel.getSettingTheme());
        setContentView(R.layout.activity_regist);
        initView();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        etAccount = (EditText) findViewById(R.id.et_account);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etPwdAgain = (EditText) findViewById(R.id.et_pwd_again);
    }

    public void confirm(View view) {
        if (checkLogin()) {
            showLodingDialog(this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);

                }
            }).start();

        } else {
            return;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                dismissDialog();
                showBottomToast("注册成功");
                SPUtils.put(RegistActivity.this, Global.SHARE_PERSONAL_ACCOUNT, account);
                SPUtils.put(RegistActivity.this, Global.SHARE_PERSONAL_PWD, pwd);
                SPUtils.put(RegistActivity.this, Global.SHARE_PERSONAL_AUTO_LOGIN, true);
                openActivity(MainActivity.class);
                RegistActivity.this.finish();
            } else
                showBottomToast("密码错误");
        }
    };

    private boolean checkLogin() {
        account = etAccount.getText().toString();
        pwd = etPwd.getText().toString();
        pwdAgain = etPwdAgain.getText().toString();
        if (TextUtils.isEmpty(account)) {
            showBottomToast("请输入账号");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            showBottomToast("请输入密码");
            return false;
        }
        if (TextUtils.isEmpty(pwdAgain)) {
            showBottomToast("请再次输入密码");
            return false;
        }
        if (pwd.equals(pwdAgain)) {
            return true;
        }
        return true;
    }


}
