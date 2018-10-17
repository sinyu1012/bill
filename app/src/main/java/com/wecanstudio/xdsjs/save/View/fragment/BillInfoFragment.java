package com.wecanstudio.xdsjs.save.View.fragment;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.wecanstudio.xdsjs.save.Model.bean.Bill;
import com.wecanstudio.xdsjs.save.Model.db.BillTableDao;
import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.View.adapter.TimeLineAdapter;
import com.wecanstudio.xdsjs.save.ViewModel.BillInfoViewModel;
import com.wecanstudio.xdsjs.save.databinding.FragmentBillinfoBinding;

/**
 * Created by xdsjs on 2015/11/28.
 */
public class BillInfoFragment extends BaseFragment<BillInfoViewModel, FragmentBillinfoBinding> implements TimeLineAdapter.OnItemClickLitener {

    public static final String ARGUMENT = "argument";
    private String mArgument;
    private ObservableList<Bill> bills;
    private PieData pieData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
            mArgument = bundle.getString(ARGUMENT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setViewModel(new BillInfoViewModel());
        setBinding(DataBindingUtil.<FragmentBillinfoBinding>inflate(getActivity().getLayoutInflater(), R.layout.fragment_billinfo, container, false));
        getBinding().setBillInfo(getViewModel());
        getViewModel().onInit(mArgument);
        return getBinding().getRoot();
    }
    TimeLineAdapter timeLineAdapter;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pieData=getViewModel().getPieDate();
        showChart(getBinding().pie, pieData);
        getBinding().recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        bills= getViewModel().getBills();
        timeLineAdapter=new TimeLineAdapter(getActivity(), bills, this);
        getBinding().recyclerview.setAdapter(timeLineAdapter);
    }

    /**
     * 传入需要的参数，设置给arguments
     *
     * @param argument
     * @return
     */
    public static BillInfoFragment newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);
        BillInfoFragment contentFragment = new BillInfoFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    //recyclerView的单击事件
    @Override
    public void onItemClick(View view, int position) {

    }

    //recyclerView的长按事件
    @Override
    public void onItemLongClick(View view, final int position) {
        AlertDialog builder=new AlertDialog.Builder(BillInfoFragment.this.getActivity())
                .setTitle("是否删除？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BillTableDao billTableDao = new BillTableDao(BillInfoFragment.this.getActivity());
                        long id=billTableDao.deleteBill(bills.get(position).getId()+"");

                        bills.remove(position);//bills 数据改变时会刷新界面
                        pieData.removeXValue(position);
                        if (id==1)
                            showToastMessage("删除成功");
                        else
                            showToastMessage("删除失败");
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();



    }

    //显示饼状图
    private void showChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(60f);  //半径
        pieChart.setTransparentCircleRadius(64f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆

        pieChart.setDescription("");

        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText("");  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }
}
