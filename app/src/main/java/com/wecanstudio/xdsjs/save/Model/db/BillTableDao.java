package com.wecanstudio.xdsjs.save.Model.db;

import android.content.Context;

import com.wecanstudio.xdsjs.save.Model.bean.Bill;

import java.util.List;

/**
 * 账单表
 * Created by xdsjs on 2015/10/14.
 */
public class BillTableDao {
    public static final String TABLE_NAME = "bill_table";
    public static final String COLUMN_NAME_ID = "id"; //主键
    public static final String COLUMN_NAME_TYPE = "type"; //记账类型
    public static final String COLUMN_NAME_MONEY = "money"; //记账金额
    public static final String COLUMN_NAME_REMARK = "remark"; //记账备注
    public static final String COLUMN_NAME_TIME = "time"; //记账时间
    public static final String COLUMN_NAME_UPLOAD = "upload";//标记是否已经备份,0未备份,1已备份

    public BillTableDao(Context context) {
        DBManager.getInstance().onInit(context);
    }

    //保存账单list
    public void saveBillList(List<Bill> bills) {
        DBManager.getInstance().saveBillList(bills);
    }

    //保存单个记录
    public void saveBill(Bill bill) {
        DBManager.getInstance().saveBill(bill);
    }

    //根据日期获取账单List
    public List<Bill> getBillList(long time) {
        return DBManager.getInstance().getBill(time);
    }

    //获取未更新到服务器的账单
    public List<Bill> getUnUploadBillList() {
        return DBManager.getInstance().getUnUploadBillList();
    }

    public long deleteBill(String id){

        long i= DBManager.getInstance().DeleteBillid(id);
        return i;
    }
    //更新账单list
    public void updateBillList(List<Bill> bills) {
        DBManager.getInstance().updateBillList(bills);
    }
}
