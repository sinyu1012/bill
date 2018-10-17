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
 * 设置安全密码
 * Created by xdsjs on 2015/11/30.
 */
public class SetBillPwdDialog extends DialogFragment {

    DialogBillpwdBinding dialogBill;
    private SetBillPwdListener setBillPwdListener;

    public SetBillPwdDialog(SetBillPwdListener setBillPwdListener) {
        this.setBillPwdListener = setBillPwdListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dialogBill = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.dialog_billpwd, null, false);
        dialogBill.etPwd.setCurrentMode(PasswordEditText.MODE_SET_PASSWORD);
        getDialog().setTitle("请输入安全密码");
        return dialogBill.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialogBill.etPwd.setCurrentMode(PasswordEditText.MODE_SET_PASSWORD);
        dialogBill.etPwd.setOnSetPwdListener(new PasswordEditText.OnSetPwdListener() {
            @Override
            public void onSetPwdFirst() {
                getDialog().setTitle("请再次输入安全密码");
            }

            @Override
            public void onSetPwdFail() {
                getDialog().setTitle("请输入安全密码");
                ToastUtils.showToast("两次输入的密码不一致,请重新输入");
            }

            @Override
            public void onSetPwdSuccess(String pwd) {
                ToastUtils.showToast("设置成功");
                setBillPwdListener.onBillPwdSetSuccess(pwd);
                getDialog().dismiss();
            }
        });

        KeyBoardUtils.openKeybord(dialogBill.etPwd, getActivity());
    }

    public interface SetBillPwdListener {
        void onBillPwdSetSuccess(String pwd);
    }
}
