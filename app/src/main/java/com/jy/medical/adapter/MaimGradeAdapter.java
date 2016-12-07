package com.jy.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.activities.MaimActivity;
import com.jy.medical.activities.SelectTreatActivity;
import com.jy.medical.adapter.viewholder.DiagnoseViewHolder;
import com.jy.medical.greendao.entities.Diagnose;
import com.jy.medical.greendao.entities.MaimGradeData;
import com.jy.medical.greendao.manager.MaimGradeDataManager;
import com.jy.medical.greendao.util.DaoUtils;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class MaimGradeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<MaimGradeData> list;
    private String taskNo;

    public MaimGradeAdapter(Context context, List<MaimGradeData> list, String taskNo) {
        this.context = context;
        this.list = list;
        this.taskNo=taskNo;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_maim_grade, null);
        return new DiagnoseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final DiagnoseViewHolder viewHolder = (DiagnoseViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaimGradeDataManager maimGradeDataManager= DaoUtils.getMaimGradeDataInstance();
                maimGradeDataManager.insertSingleData(list.get(position));
//                ((AppCompatActivity)context).finish();
                Intent intent=new Intent(context, MaimActivity.class);
                intent.putExtra("taskNo",taskNo);
                ((AppCompatActivity)context).startActivity(intent);
            }
        });

        String textTag="["+list.get(position).getDisabilityCode()+"]";
        String text=textTag+"   "+list.get(position).getDisabilityDescr();
        viewHolder.tag.setText("["+list.get(position).getDisabilityCode()+"]");
        SpannableStringBuilder style=new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#3282F0")),0,textTag.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        viewHolder.text.setText(style);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void setData(List<MaimGradeData> maimGradeDataList){
        list.addAll(maimGradeDataList);
    }
}
