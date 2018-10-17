package com.wecanstudio.xdsjs.save.ViewModel;

import android.databinding.ObservableBoolean;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import com.wecanstudio.xdsjs.save.Model.cache.SPModel;
import com.wecanstudio.xdsjs.save.View.activity.SettingActivity;
import com.wecanstudio.xdsjs.save.View.fragment.ChangeBillPwdDialog;
import com.wecanstudio.xdsjs.save.View.fragment.CheckBillPwdDialog;
import com.wecanstudio.xdsjs.save.View.fragment.SetBillPwdDialog;

/**
 * Created by xdsjs on 2015/11/29.
 */
public class SettingViewModel extends ViewModel implements SetBillPwdDialog.SetBillPwdListener, CheckBillPwdDialog.CheckBillPwdListener, ChangeBillPwdDialog.ChangeBillPwdListener {

    private SettingActivity settingActivity;
    public final ObservableBoolean setPwdCheck = new ObservableBoolean();//设置安全密码

    public SettingViewModel(SettingActivity settingActivity) {
        this.settingActivity = settingActivity;
    }

    public void initialize() {
        setPwdCheck.set(SPModel.getSettingBillPwd());
    }

    private boolean isCheck;

    //设置安全密码
    public CompoundButton.OnCheckedChangeListener onSetPwdChecked() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isCheck = isChecked;
                if (isChecked && TextUtils.isEmpty(SPModel.getPersonalBillPwd())) {
                    SetBillPwdDialog setBillPwdDialog = new SetBillPwdDialog(SettingViewModel.this);
                    setBillPwdDialog.show(settingActivity.getFragmentManager(), "setBillPwd");
                } else {
                    CheckBillPwdDialog checkBillPwdDialog = new CheckBillPwdDialog(SettingViewModel.this, SPModel.getPersonalBillPwd());
                    checkBillPwdDialog.show(settingActivity.getFragmentManager(), "checkBillPwd");
                }
            }
        };
    }

    //修改安全密码
    public View.OnClickListener onChangePwdListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!setPwdCheck.get())
                    return;
                ChangeBillPwdDialog changeBillPwdDialog = new ChangeBillPwdDialog(SettingViewModel.this, SPModel.getPersonalBillPwd());
                changeBillPwdDialog.show(settingActivity.getFragmentManager(), "changeBillPwd");
            }
        };
    }

    @Override
    public void onBillPwdChangeSuccess(String newPwd) {
        SPModel.setPersonalBillPwd(newPwd);
    }

    @Override
    public void onBillPwdCheckSuccess() {
        SPModel.setSettingBillPwd(false);
        setPwdCheck.set(false);
    }

    @Override
    public void onBillPwdCheckFailed() {
        setPwdCheck.set(!isCheck);
    }

    @Override
    public void onBillPwdSetSuccess(String pwd) {
        SPModel.setPersonalBillPwd(pwd);
        SPModel.setSettingBillPwd(true);
        setPwdCheck.set(true);
    }
}
