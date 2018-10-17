package com.wecanstudio.xdsjs.save.View.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.wecanstudio.xdsjs.save.Model.bean.Bill;
import com.wecanstudio.xdsjs.save.Model.cache.SPModel;
import com.wecanstudio.xdsjs.save.Model.db.BillTableDao;
import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.Utils.AppUtils;
import com.wecanstudio.xdsjs.save.Utils.TimeUtils;
import com.wecanstudio.xdsjs.save.View.widget.riseNum.RiseNumberTextView;
import com.wecanstudio.xdsjs.save.ViewModel.UserInfoViewModel;
import com.wecanstudio.xdsjs.save.databinding.ActivityUserInfoBinding;

import java.util.List;

public class UserInfoActivity extends BaseActivity<UserInfoViewModel, ActivityUserInfoBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.onActivityCreateSetTheme(this, SPModel.getSettingTheme());
        setContentView(R.layout.activity_user_info);
        setViewModel(new UserInfoViewModel());
        setBinding(DataBindingUtil.<ActivityUserInfoBinding>setContentView(this, R.layout.activity_user_info));
        getBinding().setUserInfo(getViewModel());
        getNum("2");
        initView();
    }

    private void initView() {
        setSupportActionBar(getBinding().toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getBinding().totalBill.withNumber(bills.size());
        getBinding().totalBill.setDuration(1500);
        getBinding().totalBill.start();
        getBinding().totalBill.setOnEndListener(new RiseNumberTextView.EndListener() {
            @Override
            public void onEndFinish() {
                startAnim();
            }
        });
    }

    private void startAnim() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(getBinding().llTwo, "alpha", 0.25f, 1, 1),
                ObjectAnimator.ofFloat(getBinding().llTwo, "scaleX", 0.5f, 1),
                ObjectAnimator.ofFloat(getBinding().llTwo, "scaleY", 0.5f, 1)
        );
        animatorSet.setDuration(1500);
        getBinding().llTwo.setVisibility(View.VISIBLE);
        animatorSet.start();

        getBinding().totalBillIn.withNumber(toin);
        getBinding().totalBillOut.withNumber(toout);
        getBinding().totalBillIn.start();
        getBinding().totalBillIn.setDuration(1500);
        getBinding().totalBillOut.start();
        getBinding().totalBillOut.setDuration(1500);
    }
    private float toin, toout;
    public final ObservableField<String> totalIn = new ObservableField<>();//总收入
    public final ObservableField<String> totalOut = new ObservableField<>();//总支出
    private List<Bill> bills;
    public void getNum(String mArgument) {//2
        BillTableDao billTableDao = new BillTableDao(this);
        if (mArgument.equals("0"))
            bills = billTableDao.getBillList(TimeUtils.getFirstDayTimeOfWeek());
        else if (mArgument.equals("1"))
            bills = billTableDao.getBillList(TimeUtils.getFirstDayTimeOfMonth());
        else
            bills = billTableDao.getBillList(TimeUtils.getFirstDayTimeOfYear());

        for (Bill bill : bills) {
            if (bill.getTypeId().equals("0")) {
                toin += Float.parseFloat(bill.getMoney());
                totalIn.set(String.valueOf(toin));
            } else {
                toout += Float.parseFloat(bill.getMoney());
                totalOut.set(String.valueOf(toout));
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
