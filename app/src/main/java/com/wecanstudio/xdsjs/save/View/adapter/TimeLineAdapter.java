package com.wecanstudio.xdsjs.save.View.adapter;

import android.content.Context;
import android.databinding.ObservableList;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.wecanstudio.xdsjs.save.Model.bean.Bill;
import com.wecanstudio.xdsjs.save.Utils.TimeUtils;
import com.wecanstudio.xdsjs.save.databinding.RecyclerTimelineItemBinding;

/**
 * Created by xdsjs on 2015/11/29.
 */
public class TimeLineAdapter extends BindingRecyclerView.ListAdapter<Bill, TimeLineAdapter.ViewHolder> {

    private OnItemClickLitener mOnItemClickLitener;
    Context context;
    public TimeLineAdapter(Context context, ObservableList<Bill> data, OnItemClickLitener mOnItemClickLitener) {
        super(context, data);
        this.context=context;
        setHasStableIds(true);
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerTimelineItemBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bill bill = data.get(position);
        holder.binding.setBill(bill);
        holder.binding.executePendingBindings();
        holder.binding.tvTypeRemark.setVisibility(TextUtils.isEmpty(data.get(position).getRemark()) ? View.GONE : View.VISIBLE);
        holder.binding.tvDate.setText(TimeUtils.getPrettyTime(Long.valueOf(data.get(position).getTime())));
    }

    //重载这个方法并设置 RecyclerView#setHasStableIds能大幅度提高性能
    @Override
    public long getItemId(int position) {
        return data.get(position).getTypeId().hashCode();
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public class ViewHolder extends BindingRecyclerView.ViewHolder<RecyclerTimelineItemBinding> {

        public ViewHolder(RecyclerTimelineItemBinding binding) {
            super(binding);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickLitener != null)
                        mOnItemClickLitener.onItemClick(v, ViewHolder.this.getLayoutPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemClickLitener != null)
                        mOnItemClickLitener.onItemLongClick(v, ViewHolder.this.getLayoutPosition());
                    return false;
                }
            });
        }
    }
}
