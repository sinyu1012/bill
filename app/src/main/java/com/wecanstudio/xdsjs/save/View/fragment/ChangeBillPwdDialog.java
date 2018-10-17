package com.wecanstudio.xdsjs.save.View.fragment;

import android.app.DialogFragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.Utils.KeyBoardUtils;
import com.wecanstudio.xdsjs.save.Utils.ToastUtils;
import com.wecanstudio.xdsjs.save.View.widget.PasswordEditText;
import com.wecanstudio.xdsjs.save.databinding.DialogBillpwdBinding;

/**
 * 修改安全密码
 * Created by xdsjs on 2015/11/30.
 */
public class ChangeBillPwdDialog extends DialogFragment {

    DialogBillpwdBinding dialogBill;
    private ChangeBillPwdListener changeBillPwdListener;
    private String pwd;

    public ChangeBillPwdDialog(ChangeBillPwdListener changeBillPwdListener, String pwd) {
        this.changeBillPwdListener = changeBillPwdListener;
        this.pwd = pwd;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dialogBill = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.dialog_billpwd, null, false);
        dialogBill.etPwd.setCurrentMode(PasswordEditText.MODE_CHECK_PASSWORD);
        dialogBill.etPwd.setPwd(pwd);
        getDialog().setTitle("请输入原有安全密码");
        return dialogBill.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialogBill.etPwd.setOnCheckPwdListener(new PasswordEditText.OnCheckPwdListener() {
            @Override
            public void onCheckSuccess() {
                ToastUtils.showToast("验证成功");
                dialogBill.etPwd.setCurrentMode(PasswordEditText.MODE_SET_PASSWORD);
                getDialog().setTitle("请输入新的安全密码");
            }

            @Override
            public void onCheckFail() {
                ToastUtils.showToast("验证失败");
            }
        });

        dialogBill.etPwd.setOnSetPwdListener(new PasswordEditText.OnSetPwdListener() {
            @Override
            public void onSetPwdFirst() {
                getDialog().setTitle("请再次输入安全密码");
            }

            @Override
            public void onSetPwdFail() {
                ToastUtils.showToast("两次输入的密码不一致,安全密码修改失败");
                getDialog().dismiss();
            }

            @Override
            public void onSetPwdSuccess(String pwd) {
                ToastUtils.showToast("安全密码修改成功");
                changeBillPwdListener.onBillPwdChangeSuccess(pwd);
                getDialog().dismiss();
            }
        });

        KeyBoardUtils.openKeybord(dialogBill.etPwd, getActivity());
    }

    public interface ChangeBillPwdListener {
        void onBillPwdChangeSuccess(String newPwd);
    }
}
