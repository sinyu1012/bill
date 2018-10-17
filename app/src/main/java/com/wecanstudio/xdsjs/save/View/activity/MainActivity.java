package com.wecanstudio.xdsjs.save.View.activity;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wecanstudio.xdsjs.save.Model.cache.SPModel;
import com.wecanstudio.xdsjs.save.Model.cache.SPUtils;
import com.wecanstudio.xdsjs.save.Model.config.Global;
import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.Utils.ActivityManager;
import com.wecanstudio.xdsjs.save.Utils.AppUtils;
import com.wecanstudio.xdsjs.save.View.adapter.ExpressionPagerAdapter;
import com.wecanstudio.xdsjs.save.View.fragment.CheckBillPwdDialog;
import com.wecanstudio.xdsjs.save.View.fragment.LoginDialogFragment;
import com.wecanstudio.xdsjs.save.ViewModel.MainPageViewModel;
import com.wecanstudio.xdsjs.save.ViewModel.UserInfoViewModel;
import com.wecanstudio.xdsjs.save.databinding.ActivityMainBinding;
import com.wecanstudio.xdsjs.save.databinding.NavHeaderMainBinding;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity<MainPageViewModel, ActivityMainBinding> implements NavigationView.OnNavigationItemSelectedListener, LoginDialogFragment.LoginListener, CheckBillPwdDialog.CheckBillPwdListener {

    private ViewPager viewPager;
    private LoginDialogFragment loginDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModel(new MainPageViewModel(this));
        AppUtils.onActivityCreateSetTheme(this,SPModel.getSettingTheme());
        setBinding(DataBindingUtil.<ActivityMainBinding>setContentView(this, R.layout.activity_main));
        getBinding().setMainPageModel(getViewModel());
        initView();
    }

    private void initView() {
        //初始化toolbar和drawerlayout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, getBinding().drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getBinding().drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        getBinding().navView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("记一笔");
        //完成对HeaderView的绑定
        NavHeaderMainBinding navBind = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header_main, null, false);
        getBinding().navView.addHeaderView(navBind.getRoot());
        navBind.setUserInfoViewModel(new UserInfoViewModel());

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        initTypeShow();
        //设置预判的记账类型
        getViewModel().setDefaultType();
    }

    /*
    初始化viewpager+gridView
     */
    private void initTypeShow() {
        final List<View> views = new ArrayList<>();

        views.add(getViewModel().getGridChildView(1));
        views.add(getViewModel().getGridChildView(2));
        views.add(getViewModel().getGridChildView(3));

        viewPager.setAdapter(new ExpressionPagerAdapter(views));

        viewPager.setOnPageChangeListener(getViewModel().getOnPagerChangeListener());
        getViewModel().setCurDial(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().killAllActivity();
    }

    /**
     * 头像的点击事件处理
     *
     * @param view
     */
    public void onAvatarClicked(View view) {
        if ((Boolean) SPUtils.get(this, Global.SHARE_PERSONAL_AUTO_LOGIN, false))
            openActivity(UserInfoActivity.class);
        else {
//            loginDialogFragment = new LoginDialogFragment();
//            loginDialogFragment.show(getFragmentManager(), "loginDialog");
            openActivity(LoginActivity.class);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

         if (id == R.id.nav_billInfo) {
                openActivity(BillInfoActivity.class);
        } else if (id == R.id.nav_theme) {
             final String[] items = new String[] { "绿色", "青色","橙色","红色","青绿色" };
             AlertDialog dialog = new AlertDialog.Builder(this).setTitle("选择主题")
                     .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             SPModel.setSettingTheme(which);
                             showMiddleToast("主题："+items[which]);
                             AppUtils.changeToTheme(MainActivity.this, which);
                             Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();
                             dialog.dismiss();
                         }
                     }).create();
             dialog.show();
//             int theme=SPModel.getSettingTheme();
//
//             if (theme>=5){
//                 theme=0;
//             }
//             theme++;
//             SPModel.setSettingTheme(theme);
//             showMiddleToast("主题"+theme);
//             AppUtils.changeToTheme(this, theme);

         } else if (id == R.id.nav_about) {
             TextView textView=new TextView(this);
             textView.setGravity(Gravity.CENTER);
             textView.setTextSize(20);
             textView.setPadding(0,20,0,0);
             textView.setText("作者：刘叶\n班级：软件1431");

             new AlertDialog.Builder(this)
                     .setTitle("关于")
                     .setView(textView)
                     .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialogInterface, int i) {

                         }
                     })
                     .show();

        } else if (id == R.id.nav_setting) {
            openActivity(Setting2Activity.class);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLoginSucceed() {
        if (loginDialogFragment != null && loginDialogFragment.isVisible())
            loginDialogFragment.dismiss();
        getViewModel().setDefaultType();
    }

    @Override
    public void onBillPwdCheckSuccess() {
        openActivity(BillInfoActivity.class);
    }

    @Override
    public void onBillPwdCheckFailed() {

    }
}
