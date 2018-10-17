package com.wecanstudio.xdsjs.save.View.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wecanstudio.xdsjs.save.Model.bean.BillType;
import com.wecanstudio.xdsjs.save.R;
import com.wecanstudio.xdsjs.save.Utils.ResourceIdUtils;

import java.util.List;

public class ExpressionAdapter extends ArrayAdapter<BillType> {

    public ExpressionAdapter(Context context, int textViewResourceId, List<BillType> billTypes) {
        super(context, textViewResourceId, billTypes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(getContext(), R.layout.gridview_item, null);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivTypeImage = (ImageView) convertView.findViewById(R.id.iv_type_avatar);
        viewHolder.tvTypeName = (TextView) convertView.findViewById(R.id.tv_type_name);
        int resId = ResourceIdUtils.getIdOfResource("type_" + getItem(position).getTypeId() + "_normal", "drawable");
        viewHolder.ivTypeImage.setImageResource(resId);
        viewHolder.tvTypeName.setText(getItem(position).getTypeName());
        return convertView;
    }

    public class ViewHolder {
        public ImageView ivTypeImage;
        public TextView tvTypeName;
    }

}
