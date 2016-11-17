package com.jy.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.greendao.entities.SelectedDepartment;
import com.jy.medical.greendao.entities.SelectedHospital;
import com.jy.medical.inter.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by songran on 16/11/16.
 */

public class SelectedHospitalAdapter extends SwipeMenuAdapter<SelectedHospitalAdapter.SelectedHospitalViewHolder> {
    public List<SelectedHospital> selectedHospitals;
    public List<SelectedDepartment>selectedDepartments;
    public Context context;
    public OnItemClickListener mOnItemClickListener;
    public SelectedHospitalAdapter(List<SelectedHospital> selectedHospitals, Context context) {
        this.selectedHospitals = selectedHospitals;
        this.context = context;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(context).inflate(R.layout.item_selected_hospital, parent,false);
    }

    @Override
    public SelectedHospitalViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new SelectedHospitalViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(SelectedHospitalViewHolder holder, int position) {
        holder.setOnItemClickListener(mOnItemClickListener);
        holder.text.setText(selectedHospitals.get(position).getHospitalName());
        holder.department.setText(selectedHospitals.get(position).getDepartmentName());
    }


    @Override
    public int getItemCount() {
        return selectedHospitals.size();
    }


    static class SelectedHospitalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView text;
        public TextView department;
        OnItemClickListener mOnItemClickListener;
        public SelectedHospitalViewHolder(View itemView) {
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
