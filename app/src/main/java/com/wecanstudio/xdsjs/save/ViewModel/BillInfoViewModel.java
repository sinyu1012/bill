package com.wecanstudio.xdsjs.save.ViewModel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.DisplayMetrics;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.wecanstudio.xdsjs.save.Model.bean.Bill;
import com.wecanstudio.xdsjs.save.Model.config.Global;
import com.wecanstudio.xdsjs.save.Model.db.BillTableDao;
import com.wecanstudio.xdsjs.save.Utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by xdsjs on 2015/11/28.
 */
public class BillInfoViewModel extends ViewModel {

    private float toin, toout;
    public final ObservableField<String> totalIn = new ObservableField<>();//总收入
    public final ObservableField<String> totalOut = new ObservableField<>();//总支出
    private List<Bill> bills;
    private HashMap<String, Float> map = new HashMap<>();
    private PieData pieData;

    public void onInit(String mArgument) {

        BillTableDao billTableDao = new BillTableDao(appContext);
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
            if (map.containsKey(bill.getTypeId()))
                map.put(bill.getTypeId(), map.get(bill.getTypeId()) + Float.parseFloat(bill.getMoney()));
            else
                map.put(bill.getTypeId(), Float.parseFloat(bill.getMoney()));
        }
        pieData = getPieData(map.size(), 100);
    }

    ObservableList<Bill> observableList = new ObservableArrayList<>();

    /**
     * 获取相应的账单list
     *
     * @return
     */
    public ObservableList<Bill> getBills() {
        observableList.addAll(bills);
        return observableList;
    }

    public PieData getPieDate() {
        return pieData;
    }

    /**
     * @param count 分成几部分
     * @param range
     */
    private PieData getPieData(int count, float range) {

        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容
        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据
        ArrayList<Integer> colors = new ArrayList<Integer>();

        Set set = map.keySet();
        Iterator iterator = set.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            String in = String.valueOf(iterator.next());
            int index = Integer.valueOf(in);
            xValues.add(Global.types[index]);
            yValues.add(new Entry(map.get(index + "") / (toin + toout), i));
            colors.add(Global.colors[index]);
            i++;
        }
        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, ""/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离
        pieDataSet.setColors(colors);

        DisplayMetrics metrics = appContext.getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }
}
