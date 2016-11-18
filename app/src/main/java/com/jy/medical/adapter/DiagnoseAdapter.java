package com.jy.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.activities.SelectTreatActivity;
import com.jy.medical.adapter.viewholder.DiagnoseViewHolder;
import com.jy.medical.adapter.viewholder.HospitalViewHolder;
import com.jy.medical.greendao.entities.Diagnose;
import com.jy.medical.greendao.entities.HospitalData;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class DiagnoseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Diagnose> list;
    private String taskNo;

    public DiagnoseAdapter(Context context, List<Diagnose> list,String taskNo) {
        this.context = context;
        this.list = list;
        this.taskNo=taskNo;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_diagnose, null);
        return new DiagnoseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final DiagnoseViewHolder viewHolder = (DiagnoseViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SelectTreatActivity.class);
                intent.putExtra("taskNo",taskNo);
                intent.putExtra("diagnoseId",list.get(position).getDiagnoseId());
                intent.putExtra("diagnoseName",list.get(position).getItemCnName());
                context.startActivity(intent);
            }
        });
        viewHolder.text.setText(list.get(position).getItemCnName());
        viewHolder.tag.setText("["+list.get(position).getItemCode()+"]");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void setData(List<Diagnose> diagnoseList){
        list.addAll(diagnoseList);
    }
}
