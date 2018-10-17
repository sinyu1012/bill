package com.wecanstudio.xdsjs.save.Model.config;

import android.graphics.Color;

/**
 * 这里保存一些全局的参数
 * Created by xdsjs on 2015/10/14.
 */
public class Global {
    /**
     * 记账类型的数组
     */
    public static final String[] types = new String[]{
            "个人收入", "早午晚餐", "公共交通", "水果零食",
            "衣服鞋帽", "日常用品", "休闲聚餐", "运动健身",
            "网上购物", "打车租车", "交流通讯", "旅游度假",
            "化妆饰品", "数码设备", "通讯上网", "邮寄快递",
            "宠物宝贝", "水电煤气", "学习培训", "其他杂项"
    };

    public static final int[] colors = new int[]{
            Color.rgb(255, 136, 34), Color.rgb(234, 103, 68), Color.rgb(118, 136, 242),
            Color.rgb(247, 186, 91), Color.rgb(245, 113, 110), Color.rgb(102, 204, 238),
            Color.rgb(44, 216, 101), Color.rgb(255, 147, 54), Color.rgb(255, 183, 0),
            Color.rgb(255, 103, 185), Color.rgb(86, 178, 255), Color.rgb(102, 204, 238),
            Color.rgb(252, 88, 48), Color.rgb(72, 217, 207), Color.rgb(146, 141, 255),
            Color.rgb(110, 207, 239), Color.rgb(255, 105, 105), Color.rgb(255, 136, 34),
            Color.rgb(63, 171, 233), Color.rgb(88, 200, 77)
    };
    /**
     * 要缓存的设置信息
     */
    public static final String SHARE_SETTING_UPLOAD = "setting_upload";//设置是否上传
    public static final String SHARE_SETTING_BILL_PWD = "setting_bill_pwd";//设置是否需要设置安全密码
    public static final String SHARE_SETTING_BUDGET = "setting_budget";//设置预算
    /**
     * 要缓存的个人信息
     */
    public static final String SHARE_PERSONAL_AVATAR = "personal_avatar";//头像
    public static final String SHARE_PERSONAL_ACCOUNT = "personal_account";//个人账号
    public static final String SHARE_PERSONAL_PWD = "personal_pwd";//个人密码
    public static final String SHARE_PERSONAL_BILL_PWD = "personal_bill_pwd";//个人安全密码
    public static final String SHARE_PERSONAL_TOTAL_IN = "personal_total_in";//个人历史总收入
    public static final String SHARE_PERSONAL_TOTAL_OUT = "personal_total_out";//个人历史总支出
    public static final String SHARE_PERSINAL_TOKEN = "personal_token";//token

    public static final String SHARE_PERSONAL_AUTO_LOGIN = "personal_auto_login";//标记是否自动登录
    public static final String SHARE_PERSONAL_THEME = "personal_theme";//切换主题

}
