package com.jy.medical.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.greendao.entities.SelectedDepartment;
import com.jy.medical.greendao.entities.SelectedDiagnose;
import com.jy.medical.greendao.entities.SelectedHospital;
import com.jy.medical.inter.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

/**
 * Created by songran on 16/11/16.
 */

public class SelectedDiagnoseAdapter extends SwipeMenuAdapter<SelectedDiagnoseAdapter.SelectedDiagnoseViewHolder> {
    public List<SelectedDiagnose> selectedDiagnoseList;
    public Context context;
    public OnItemClickListener mOnItemClickListener;
    public SelectedDiagnoseAdapter(List<SelectedDiagnose> selectedDiagnoseList, Context context) {
        this.selectedDiagnoseList = selectedDiagnoseList;
        this.context = context;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(context).inflate(R.layout.item_selected_diagnose, null,false);
    }

    @Override
    public SelectedDiagnoseViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        return new SelectedDiagnoseViewHolder(realContentView);
    }

    @Override
    public void onBindViewHolder(SelectedDiagnoseViewHolder holder, int position) {
        holder.setOnItemClickListener(mOnItemClickListener);
        holder.text.setText(selectedDiagnoseList.get(position).getDiagnoseName());
        holder.department.setText(selectedDiagnoseList.get(position).getTreatmentModeName());
    }


    @Override
    public int getItemCount() {
        return selectedDiagnoseList.size();
    }


    static class SelectedDiagnoseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView text;
        public TextView department;
        OnItemClickListener mOnItemClickListener;
        public SelectedDiagnoseViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            text = (TextView) itemView.findViewById(R.id.diagnose_text);
            department = (TextView) itemView.findViewById(R.id.treat_text);
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
