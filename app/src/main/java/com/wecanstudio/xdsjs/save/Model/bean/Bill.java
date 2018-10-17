package com.wecanstudio.xdsjs.save.Model.bean;

import android.graphics.drawable.Drawable;

import com.wecanstudio.xdsjs.save.Model.config.Global;
import com.wecanstudio.xdsjs.save.MyApplication;
import com.wecanstudio.xdsjs.save.Utils.ResourceIdUtils;

/**
 * 账单
 * Created by xdsjs on 2015/10/14.
 */
public class Bill {
    private String Id;
    private String typeId;
    private String money;
    private String remark;
    private String time;
    private int upload = 0;//0表示未更新，1表示已更新
    private String typeName;//记账种类的名称
    private Drawable typeDrawable;//记账种类对应的图像

    public Bill() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Bill(String typeId, String money, String remark, String time, int upload) {
        this.typeId = typeId;
        this.money = money;
        this.remark = remark;
        this.time = time;
        this.upload = upload;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUpload() {
        return upload;
    }

    public void setUpload(int upload) {
        this.upload = upload;
    }

    public String getTypeName() {
        return Global.types[Integer.valueOf(this.getTypeId())];
    }

    public Drawable getTypeDrawable() {
        int resId = ResourceIdUtils.getIdOfResource("type_" + this.getTypeId() + "_normal", "drawable");
        return MyApplication.getContext().getResources().getDrawable(resId);
    }

    @Override
    public String toString() {
        return "typeId:--->" + this.getTypeId() + "\n" +
                "price:--->" + this.getMoney();
    }
}
