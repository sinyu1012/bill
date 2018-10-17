package com.wecanstudio.xdsjs.save.ViewModel;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;

import com.wecanstudio.xdsjs.save.Model.bean.RegistResponse;
import com.wecanstudio.xdsjs.save.Model.bean.Register;
import com.wecanstudio.xdsjs.save.Model.bean.User;
import com.wecanstudio.xdsjs.save.Model.cache.SPUtils;
import com.wecanstudio.xdsjs.save.Model.config.Global;
import com.wecanstudio.xdsjs.save.Model.net.RestApi;
import com.wecanstudio.xdsjs.save.Model.net.RxNetworking;
import com.wecanstudio.xdsjs.save.MyApplication;
import com.wecanstudio.xdsjs.save.Utils.NetUtils;
import com.wecanstudio.xdsjs.save.Utils.ToastUtils;
import com.wecanstudio.xdsjs.save.View.fragment.LoginDialogFragment;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xdsjs on 2015/11/23.
 */
public class LoginViewModel extends LoadingViewModel {

    private LoginDialogFragment loginDialogFragment;

    public LoginViewModel(LoginDialogFragment loginDialogFragment) {
        this.loginDialogFragment = loginDialogFragment;
    }

    @Override
    public View.OnClickListener onRetryClick() {
        return null;
    }

    /**
     * 处理登陆事件
     *
     * @param account
     * @param pwd
     */
    @Command
    public void doLogin(final String account, final String pwd) {
        User user = new User(account, pwd);
        ProgressDialog pd = new ProgressDialog(loginDialogFragment.getActivity());
//        Observable.Transformer<LoginResponse, LoginResponse> networkingIndicator = RxNetworking.bindConnecting(pd);
        pd.show();

        if (account.equals("admin")&&pwd.equals("123456")) {
            //缓存个人信息
            pd.dismiss();
//            SPUtils.put(appContext, Global.SHARE_PERSINAL_TOKEN, response.getAccess_token());
            SPUtils.put(appContext, Global.SHARE_PERSONAL_ACCOUNT, account);
            SPUtils.put(appContext, Global.SHARE_PERSONAL_PWD, pwd);
            SPUtils.put(appContext, Global.SHARE_PERSONAL_AUTO_LOGIN, true);
            LoginDialogFragment.LoginListener loginListener = (LoginDialogFragment.LoginListener) loginDialogFragment.getActivity();
            loginListener.onLoginSucceed();
        } else{
            pd.dismiss();
            Log.e("Login", "该用户不存在");
            ToastUtils.showToast("该用户不存在");
        }

    }


    @Command
    public void doRegist(final String account, final String pwd) {
        final Register register = new Register(account, pwd, "123456");
        ProgressDialog pd = new ProgressDialog(loginDialogFragment.getActivity());
        Observable.Transformer<RegistResponse, RegistResponse> networkingIndicator = RxNetworking.bindConnecting(pd);
        MyApplication.getInstance().createApi(RestApi.class)
                .regist(register)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(networkingIndicator)
                .subscribe(new Observer<RegistResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (NetUtils.isConnected(appContext))
                            ToastUtils.showToast("注册失败,该用户已存在");
                        else
                            ToastUtils.showToast("请检查网络连接-_-");
                    }

                    @Override
                    public void onNext(RegistResponse response) {
                        if (response.getStatus().equals("1"))
                            doLogin(account, pwd);
                    }
                });
    }
}