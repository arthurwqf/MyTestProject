package com.qingfeng.mytest.common;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by WangQF on 2017/12/20 0020.
 */

public class CommonRecyclerViewAdapter extends RecyclerView.Adapter<CommonRecyclerViewAdapter.CommonViewHolder> {
    private Context mContext;
    private List mData;
    private int mItemLayoutId;
    private int mViewVariableId;
    private OnItemClickListener listener;

    public CommonRecyclerViewAdapter(Context mContext, List mData, int mItemLayoutId, int mViewVariableId) {
        this.mContext = mContext;
        this.mData = mData;
        this.mItemLayoutId = mItemLayoutId;
        this.mViewVariableId = mViewVariableId;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                mItemLayoutId, parent, false);
        CommonViewHolder holder = new CommonViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        holder.binding.setVariable(mViewVariableId, mData.get(position));
        holder.binding.executePendingBindings();
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return null == mData ? 0 : mData.size();
    }

    class CommonViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;

        public CommonViewHolder(View itemView) {
            super(itemView);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}
