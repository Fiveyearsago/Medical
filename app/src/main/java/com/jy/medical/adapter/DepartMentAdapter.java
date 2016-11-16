package com.jy.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jy.medical.R;
import com.jy.medical.activities.SelectDepartmentsActivity;
import com.jy.medical.adapter.viewholder.DepartmentViewHolder;
import com.jy.medical.adapter.viewholder.HospitalViewHolder;
import com.jy.medical.greendao.entities.HospitalData;
import com.jy.medical.greendao.entities.MedicalDepartment;
import com.jy.medical.util.PublicString;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class DepartmentAdapter extends BaseHeadFootAdapter {
    private Context context;
    private List<MedicalDepartment> list;
    private String taskNo;
    private NotifyCallBack notifyCallBack;

    public DepartmentAdapter(Context context, List<MedicalDepartment> list,NotifyCallBack notifyCallBack) {
        this.context = context;
        this.list = list;
        this.notifyCallBack=notifyCallBack;
    }


    @Override
    protected void onBindHeaderView(View headerView) {
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    protected void onBindFooterView(View footerView) {
    }


    @Override
    protected int getItemNum() {
        return list.size();
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, final int position) {
        final DepartmentViewHolder viewHolder = (DepartmentViewHolder) holder;
        viewHolder.setIsRecyclable(false);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"click",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.text.setText(list.get(position).getValue());
        viewHolder.checkBox.setChecked(false);
        viewHolder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"click",Toast.LENGTH_SHORT).show();
                viewHolder.checkBox.setChecked(!viewHolder.checkBox.isChecked());
                notifyCallBack.changeHeadInfo(list.get(position),viewHolder.checkBox.isChecked());
            }
        });
    }

    @Override
    public DepartmentViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_department, null);
        view.setBackground(context.getResources().getDrawable(R.drawable.item_click_bg_selector));
        return new DepartmentViewHolder(view);
    }
    public interface NotifyCallBack{
        void changeHeadInfo(MedicalDepartment medicalDepartment,boolean isChecked);
    }
}
