package com.wecanstudio.xdsjs.save.Model.bean;

import android.graphics.drawable.Drawable;

import com.wecanstudio.xdsjs.save.MyApplication;
import com.wecanstudio.xdsjs.save.Utils.ResourceIdUtils;

/**
 * 表示每一种记账种类
 * Created by xdsjs on 2015/10/20.
 */
public class BillType {
    private int typeId;//记账种类对应的Id
    private int time;//对应次数

    private String typeName;//类型名称

    private Drawable typeDrawable;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }


    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Drawable getTypeDrawable() {
        int resId = ResourceIdUtils.getIdOfResource("type_" + this.getTypeId() + "_normal", "drawable");
        return MyApplication.getContext().getResources().getDrawable(resId);
    }

    @Override
    public String toString() {
        return "name:" + this.getTypeName() + "typeId:" + this.getTypeId() + "time:" + this.getTime();
    }
}
