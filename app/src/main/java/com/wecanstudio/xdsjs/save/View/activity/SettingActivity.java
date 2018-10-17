package com.wecanstudio.xdsjs.save.View.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.balysv.materialripple.MaterialRippleLayout;
import com.wecanstudio.xdsjs.save.Model.cache.SPUtils;
import com.wecanstudio.xdsjs.save.Model.config.Global;
import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.ViewModel.SettingViewModel;
import com.wecanstudio.xdsjs.save.databinding.ActivitySettingBinding;

public class SettingActivity extends BaseActivity<SettingViewModel, ActivitySettingBinding> {

    private MaterialRippleLayout lv_tuichu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModel(new SettingViewModel(this));
        setBinding(DataBindingUtil.<ActivitySettingBinding>setContentView(this, R.layout.activity_setting));
        getBinding().setSettingViewModel(getViewModel());
        initView();
    }

    private void initView() {
        setSupportActionBar(getBinding().toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("设置");
        lv_tuichu= (MaterialRippleLayout) findViewById(R.id.lv_tuichu);
        lv_tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils.put(SettingActivity.this, Global.SHARE_PERSONAL_AUTO_LOGIN, false);
                openActivity(LoginActivity.class);
                SettingActivity.this.finish();
            }
        });
        getViewModel().initialize();
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
