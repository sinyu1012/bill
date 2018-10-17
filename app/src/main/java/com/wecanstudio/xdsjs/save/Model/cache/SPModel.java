package com.wecanstudio.xdsjs.save.Model.cache;

import android.content.Context;

import com.wecanstudio.xdsjs.save.Model.config.Global;
import com.wecanstudio.xdsjs.save.MyApplication;


/**
 * 默认的设置
 * Created by xdsjs on 2015/10/14.
 */
public class SPModel {

    private static Context context = MyApplication.getContext();

    public static void setSettingUpLoad(boolean isUpLoad) {
        SPUtils.put(context, Global.SHARE_SETTING_UPLOAD, isUpLoad);
    }

    public static boolean getSettingUpLoad() {
        return (boolean) SPUtils.get(context, Global.SHARE_SETTING_UPLOAD, true);
    }

    public static void setSettingBillPwd(boolean isBillPwd) {
        SPUtils.put(context, Global.SHARE_SETTING_BILL_PWD, isBillPwd);
    }

    public static boolean getSettingBillPwd() {
        return (boolean) SPUtils.get(context, Global.SHARE_SETTING_BILL_PWD, false);
    }

    public static void setSettingBudget(float budget) {
        SPUtils.put(context, Global.SHARE_SETTING_BUDGET, budget);
    }

    public static float getSettingBudget() {
        return (float) SPUtils.get(context, Global.SHARE_SETTING_BUDGET, 0.0f);
    }

    public static void setSettingTheme(int theme) {
        SPUtils.put(context, Global.SHARE_PERSONAL_THEME, theme);
    }

    public static int getSettingTheme() {
        return (int) SPUtils.get(context, Global.SHARE_PERSONAL_THEME, 1);
    }
    /**
     * 对用户数据的缓存
     */
    public static void setPersonalAccount(String account) {
        SPUtils.put(context, Global.SHARE_PERSONAL_ACCOUNT, account);
    }

    public static String getPersonalAccount() {
        return (String) SPUtils.get(context, Global.SHARE_PERSONAL_ACCOUNT, "");
    }

    public static void setPersonalPwd(String pwd) {
        SPUtils.put(context, Global.SHARE_PERSONAL_PWD, pwd);
    }

    public static String getPersonalPwd() {
        return (String) SPUtils.get(context, Global.SHARE_PERSONAL_PWD, "");
    }

    public static void setPersonalBillPwd(String billPwd) {
        SPUtils.put(context, Global.SHARE_PERSONAL_BILL_PWD, billPwd);
    }

    public static String getPersonalBillPwd() {
        return (String) SPUtils.get(context, Global.SHARE_PERSONAL_BILL_PWD, "");
    }

    public static void setPersonalTotalIn(String totalIn) {
        SPUtils.put(context, Global.SHARE_PERSONAL_TOTAL_IN, totalIn);
    }

    public static String getPersonalTotalIn() {
        return (String) SPUtils.get(context, Global.SHARE_PERSONAL_TOTAL_IN, "0.0");
    }

    public static void setPersonalTotalOut(String totalOut) {
        SPUtils.put(context, Global.SHARE_PERSONAL_TOTAL_OUT, totalOut);
    }

    public static String getPersonalTotalOut() {
        return (String) SPUtils.get(context, Global.SHARE_PERSONAL_TOTAL_OUT, "0.0");
    }

    public static void setPersonalAutoLogin(boolean autoLogin) {
        SPUtils.put(context, Global.SHARE_PERSONAL_AUTO_LOGIN, autoLogin);
    }

    public static boolean getPersonalAutoLogin() {
        return (boolean) SPUtils.get(context, Global.SHARE_PERSONAL_AUTO_LOGIN, false);
    }
}