package com.qingfeng.mytest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by WangQF on 2017/11/16 0016.
 */

public class AdapterRecyclerViewImg extends RecyclerView.Adapter<AdapterRecyclerViewImg.ImgViewHolder> {
    private Context mContext;
    private ArrayList<Integer> imgPaths;
    private OnItemClickListener onItemClickListener;

    public AdapterRecyclerViewImg(Context mContext, ArrayList<Integer> paths) {
        this.mContext = mContext;
        this.imgPaths = paths;
    }

    @Override
    public ImgViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImgViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_img, parent, false));
    }

    @Override
    public void onBindViewHolder(ImgViewHolder holder, final int position) {
        Glide.with(mContext)
                .load(imgPaths.get(position))
//                .bitmapTransform(new CropCircleTransformation(this))
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imgPaths.size();
    }

    public class ImgViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImgViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}
