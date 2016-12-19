package com.jy.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.greendao.entities.NursingData;
import com.jy.medical.inter.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by songran on 16/11/16.
 */

public class SelectedNursingAdapter extends SwipeMenuAdapter<SelectedNursingAdapter.SelectedNursingViewHolder> {
    public List<NursingData> selectedNursings;
    public Context context;
    public OnItemClickListener mOnItemClickListener;
    public SelectedNursingAdapter(List<NursingData> selectedNursings, Context context) {
        this.selectedNursings = selectedNursings;
        this.context = context;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(context).inflate(R.layout.item_selected_nursing, null,false);
    }

    @Override
    public SelectedNursingViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new SelectedNursingViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(SelectedNursingViewHolder holder, int position) {
        holder.setOnItemClickListener(mOnItemClickListener);
        holder.nursingName.setText(selectedNursings.get(position).getName());
        holder.nursingId.setText("("+selectedNursings.get(position).getIdValue()+")");
        holder.nursingFee.setText("￥"+selectedNursings.get(position).getFee());
        holder.nursingDays.setText(selectedNursings.get(position).getDays());
    }


    @Override
    public int getItemCount() {
        return selectedNursings.size();
    }


    static class SelectedNursingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView nursingName;
        public TextView nursingId;
        public TextView nursingDays;
        public TextView nursingFee;
        OnItemClickListener mOnItemClickListener;
        public SelectedNursingViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            nursingName = (TextView) itemView.findViewById(R.id.nursing_name);
            nursingId = (TextView) itemView.findViewById(R.id.nursing_id);
            nursingDays = (TextView) itemView.findViewById(R.id.nursing_days);
            nursingFee = (TextView) itemView.findViewById(R.id.nursing_fee);
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
