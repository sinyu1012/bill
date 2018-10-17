package com.wecanstudio.xdsjs.save.View.fragment;

import android.app.DialogFragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.Utils.ToastUtils;
import com.wecanstudio.xdsjs.save.ViewModel.LoginViewModel;
import com.wecanstudio.xdsjs.save.databinding.DialogLoginBinding;

/**
 * Created by xdsjs on 2015/11/27.
 */
public class LoginDialogFragment extends DialogFragment {
    private DialogLoginBinding dialogBind;
    private LoginViewModel loginViewModel;

    private static final int LOGIN = 0;//登陆按钮可点击状态
    private static final int REGIST = 1;
    private static final int NORMAL = -1;
    private int currentMode = NORMAL;

    private String account;
    private String pwd, pwdAgain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("登录或注册");
        dialogBind = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.dialog_login, null, false);
        loginViewModel = new LoginViewModel(this);
        dialogBind.setLoginViewModel(loginViewModel);
        return dialogBind.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogBind.account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dialogBind.pwd.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dialogBind.pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (getCurrentMode() != REGIST)
                    setCurrentMode(LOGIN);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dialogBind.regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBind.pwd.setVisibility(View.VISIBLE);
                dialogBind.pwdAgain.setVisibility(View.VISIBLE);
                dialogBind.regist.setVisibility(View.GONE);
                dialogBind.login.setText("完成");
                getDialog().setTitle("注册");
                setCurrentMode(REGIST);
            }
        });

        dialogBind.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (getCurrentMode()) {
                    case REGIST:
                        if (checkRegist())
                            loginViewModel.doRegist(account, pwd);
                        break;
                    case LOGIN:
                        if (checkLogin())
                            loginViewModel.doLogin(account, pwd);
                        break;
                }
            }
        });
    }

    private void setCurrentMode(int mode) {
        currentMode = mode;
    }

    private int getCurrentMode() {
        return currentMode;
    }

    private boolean checkLogin() {
        account = dialogBind.account.getText().toString().trim();
        pwd = dialogBind.pwd.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showToast("请输入账号");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showToast("请输入密码");
            return false;
        }
        return true;
    }

    private boolean checkRegist() {
        account = dialogBind.account.getText().toString().trim();
        pwd = dialogBind.pwd.getText().toString().trim();
        pwdAgain = dialogBind.pwdAgain.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showToast("请输入账号");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showToast("请输入密码");
            return false;
        }
        if (TextUtils.isEmpty(pwdAgain)) {
            ToastUtils.showToast("请再次输入密码");
            return false;
        }
        if (pwd.equals(pwdAgain)) {
            return true;
        }
        return true;
    }

    public interface LoginListener {

        void onLoginSucceed();
    }
}
