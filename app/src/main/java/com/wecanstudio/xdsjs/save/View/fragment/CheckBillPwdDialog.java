package com.wecanstudio.xdsjs.save.View.fragment;

import android.app.DialogFragment;
import android.content.DialogInterface;
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
 * 检验安全密码
 * Created by xdsjs on 2015/11/27.
 */
public class CheckBillPwdDialog extends DialogFragment {
    DialogBillpwdBinding dialogBill;
    private CheckBillPwdListener billPwdListener;
    private String pwd;//当前已经设置的安全密码

    public CheckBillPwdDialog(CheckBillPwdListener billPwdListener, String pwd) {
        this.billPwdListener = billPwdListener;
        this.pwd = pwd;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dialogBill = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.dialog_billpwd, null, false);
        dialogBill.etPwd.setCurrentMode(PasswordEditText.MODE_CHECK_PASSWORD);
        dialogBill.etPwd.setPwd(pwd);
        getDialog().setTitle("请输入安全密码");
        return dialogBill.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogBill.etPwd.setOnCheckPwdListener(new PasswordEditText.OnCheckPwdListener() {
            @Override
            public void onCheckSuccess() {
                ToastUtils.showToast("验证成功");
                getDialog().dismiss();
                billPwdListener.onBillPwdCheckSuccess();
            }

            @Override
            public void onCheckFail() {
                ToastUtils.showToast("验证失败");
            }
        });

        KeyBoardUtils.openKeybord(dialogBill.etPwd, getActivity());
    }

    public interface CheckBillPwdListener {
        void onBillPwdCheckSuccess();

        void onBillPwdCheckFailed();
    }
}
