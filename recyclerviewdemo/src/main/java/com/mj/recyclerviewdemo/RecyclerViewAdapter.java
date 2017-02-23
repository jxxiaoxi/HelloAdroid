package com.mj.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by liuwei on 1/7/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RVViewHolder> {
    private ArrayList<String> mData;
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<String> data) {
        mData = data;
        mContext = context;
    }

    @Override
    public RecyclerViewAdapter.RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_layout, parent, false);//View.inflate(mContext,R.layout.recycler_view_item_layout,parent);
        RVViewHolder viewHolder = new RVViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RVViewHolder holder, int position) {
        holder.tv_context.setText(mData.get(position));
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    class RVViewHolder extends RecyclerView.ViewHolder {
        TextView tv_context;

        public RVViewHolder(View itemView) {
            super(itemView);
            tv_context = (TextView) itemView.findViewById(R.id.tv_context);
        }
    }


}
