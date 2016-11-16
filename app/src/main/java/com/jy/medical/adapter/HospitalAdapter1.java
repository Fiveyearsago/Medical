package com.jy.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.adapter.viewholder.HospitalViewHolder;
import com.jy.medical.greendao.entities.HospitalData;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class HospitalAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<HospitalData> list;

    public HospitalAdapter1(Context context, List<HospitalData> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hospital, null);
        return new HospitalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final HospitalViewHolder viewHolder = (HospitalViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        viewHolder.text.setText(list.get(position).getHospitalName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
