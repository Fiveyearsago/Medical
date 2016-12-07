package com.jy.medical.activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.greendao.entities.MaimGradeData;
import com.jy.medical.inter.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by songran on 16/11/16.
 */

public class SelectedMaimGradeAdapter extends SwipeMenuAdapter<SelectedMaimGradeAdapter.MaimGradeDataViewHolder> {
    public List<MaimGradeData> maimGradeDatas;
    public Context context;
    public OnItemClickListener mOnItemClickListener;
    public SelectedMaimGradeAdapter(List<MaimGradeData> maimGradeDatas, Context context) {
        this.maimGradeDatas = maimGradeDatas;
        this.context = context;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public void setDta(List<MaimGradeData> maimGradeDatas){
        this.maimGradeDatas = maimGradeDatas;
    }
    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(context).inflate(R.layout.item_selected_hospital, null,false);
    }

    @Override
    public MaimGradeDataViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new MaimGradeDataViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(MaimGradeDataViewHolder holder, int position) {
        holder.setOnItemClickListener(mOnItemClickListener);
        holder.text.setText(maimGradeDatas.get(position).getDisabilityCode());
        holder.department.setText(maimGradeDatas.get(position).getDisabilityDescr());
    }


    @Override
    public int getItemCount() {
        return maimGradeDatas.size();
    }


    static class MaimGradeDataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView text;
        public TextView department;
        OnItemClickListener mOnItemClickListener;
        public MaimGradeDataViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            text = (TextView) itemView.findViewById(R.id.hospital_text);
            department = (TextView) itemView.findViewById(R.id.department_text);
        }
        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
