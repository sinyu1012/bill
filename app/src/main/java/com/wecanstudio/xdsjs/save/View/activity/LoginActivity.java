package com.wecanstudio.xdsjs.save.View.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wecanstudio.xdsjs.save.Model.cache.SPModel;
import com.wecanstudio.xdsjs.save.Model.cache.SPUtils;
import com.wecanstudio.xdsjs.save.Model.config.Global;
import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.Utils.AppUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText etAccount, etPwd;
    private String account, pwd;

    private ImageView ivBack;
    private TextView tvRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.onActivityCreateSetTheme(this, SPModel.getSettingTheme());
        setContentView(R.layout.activity_login);
        initView();
    }
    private void initView() {
        etAccount = (EditText) findViewById(R.id.et_account);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvRegist = (TextView) findViewById(R.id.tv_regist);
        ivBack.setOnClickListener(this);
        tvRegist.setOnClickListener(this);
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
                    if (account.equals(SPUtils.get(LoginActivity.this, Global.SHARE_PERSONAL_ACCOUNT, "admin")) && pwd.equals(SPUtils.get(LoginActivity.this, Global.SHARE_PERSONAL_PWD, "123"))) {
                        Message message=new Message();
                        message.what=1;
                        handler.sendMessage(message);

                    }else
                    {
                        Message message=new Message();
                        message.what=0;
                        handler.sendMessage(message);

                    }

                }
            }).start();


           // doLogin(account, pwd);
        } else {
            return;
        }
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            dismissDialog();
            if (msg.what==1){
                showBottomToast("登录成功");
                SPUtils.put(LoginActivity.this, Global.SHARE_PERSONAL_ACCOUNT, account);
                SPUtils.put(LoginActivity.this, Global.SHARE_PERSONAL_PWD, pwd);
                SPUtils.put(LoginActivity.this, Global.SHARE_PERSONAL_AUTO_LOGIN, true);
                openActivity(MainActivity.class);
                LoginActivity.this.finish();
            }else
                showBottomToast("密码错误");
        }
    };
    private boolean checkLogin() {
        account = etAccount.getText().toString();
        pwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(account)) {
            showBottomToast("请输入账号");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            showBottomToast("请输入密码");
            return false;
        }
        return true;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                LoginActivity.this.finish();
                break;
            case R.id.tv_regist:
                openActivity(RegistActivity.class);
                break;
        }
    }
}
