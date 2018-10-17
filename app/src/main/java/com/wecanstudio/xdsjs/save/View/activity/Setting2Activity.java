package com.wecanstudio.xdsjs.save.View.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wecanstudio.xdsjs.save.Model.cache.SPModel;
import com.wecanstudio.xdsjs.save.Model.cache.SPUtils;
import com.wecanstudio.xdsjs.save.Model.config.Global;
import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.Utils.AppUtils;
import com.wecanstudio.xdsjs.save.View.widget.ToggleButton;

public class Setting2Activity extends BaseActivity implements View.OnClickListener {


    private ImageView ivBack;
    private ToggleButton tbPwd;
    private RelativeLayout rlChangePwd;
    private RelativeLayout rlAbout;
    private ToggleButton tbUpdate;
    private Button btnLogout;
    private TextView tvBudget,tv_username;
    private RelativeLayout rlBudget;

    private SPModel myModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppUtils.onActivityCreateSetTheme(this,SPModel.getSettingTheme());
        setContentView(R.layout.activity_setting2);

        initView();
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tbPwd = (ToggleButton) findViewById(R.id.tb_pwd);
        rlChangePwd = (RelativeLayout) findViewById(R.id.rl_pwd_change);
        rlAbout = (RelativeLayout) findViewById(R.id.rl_about);
        tbUpdate = (ToggleButton) findViewById(R.id.tbUpdate);
        btnLogout = (Button) findViewById(R.id.btn_logout);
        tvBudget = (TextView) findViewById(R.id.tvBudget);
        rlBudget = (RelativeLayout) findViewById(R.id.rl_budget);
        tv_username= (TextView) findViewById(R.id.tv_username);
        tv_username.setText(SPModel.getPersonalAccount());
//        ivBack.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        rlChangePwd.setOnClickListener(this);
        rlAbout.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        rlBudget.setOnClickListener(this);

        tbPwd.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                myModel.setSettingBillPwd(on);
            }
        });

        tbUpdate.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                myModel.setSettingUpLoad(on);
            }
        });
       // myModel =
        tbPwd.setToggleOn(myModel.getSettingBillPwd());
        tbUpdate.setToggleOn(myModel.getSettingUpLoad());
        tvBudget.setText(String.valueOf(myModel.getSettingBudget()));

        if (myModel.getPersonalAutoLogin()) {
            btnLogout.setText("退出登陆");
            btnLogout.setBackgroundResource(R.drawable.btn_logout);
        } else {
            btnLogout.setText("登陆");
            btnLogout.setBackgroundResource(R.drawable.btn_login);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.rl_about:  TextView textView=new TextView(this);
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(20);
                textView.setPadding(0,20,0,0);
                textView.setText("作者：董函\n班级：软件1431");

                new AlertDialog.Builder(this)
                        .setTitle("关于")
                        .setView(textView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();

                break;
            case R.id.rl_pwd_change:
                final EditText editText1=new EditText(this);
                //editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                AlertDialog builder1=new AlertDialog.Builder(this)
                        .setTitle("请输入原密码")
                        .setView(editText1)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               if (editText1.getText().toString().equals(SPModel.getPersonalPwd())){
                                   final EditText editText1=new EditText(Setting2Activity.this);
                                   //editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                                   AlertDialog builder1=new AlertDialog.Builder(Setting2Activity.this)
                                           .setTitle("请输入新密码")
                                           .setView(editText1)
                                           .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                               @Override
                                               public void onClick(DialogInterface dialogInterface, int i) {
                                                  SPModel.setPersonalPwd(editText1.getText().toString());
                                                   showMiddleToast("修改成功");
                                               }
                                           })
                                           .show();
                               }else{
                                   showMiddleToast("密码错误");
                               }
                            }
                        })
                        .show();
                break;
            case R.id.rl_budget:
                final EditText editText=new EditText(this);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                AlertDialog builder=new AlertDialog.Builder(this)
                        .setTitle("请输入您的预算")
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!TextUtils.isEmpty(editText.getText().toString())) {
                                    SPModel.setSettingBudget(Float.valueOf(editText.getText().toString()));
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tvBudget.setText(editText.getText().toString());
                                        }
                                    });
                                }
                            }
                        })
                        .show();
                break;
            case R.id.btn_logout:
                if (btnLogout.getText().equals("退出登陆")) {
                    btnLogout.setText("登陆");
                    btnLogout.setBackgroundResource(R.drawable.btn_login);
                    //((MyController) BaseController.getInstance()).logOut();
                } else if (btnLogout.getText().equals("登陆")) {
                    SPUtils.put(Setting2Activity.this, Global.SHARE_PERSONAL_AUTO_LOGIN, false);
                    openActivity(LoginActivity.class);
                    Setting2Activity.this.finish();
                }
                break;
        }
    }
}
