package com.jy.medical.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.activities.AddContactsActivity;
import com.jy.medical.activities.LocalAlbumActivity;
import com.jy.medical.activities.SelectDepartmentsActivity;
import com.jy.medical.activities.SelectHospitalActivity;
import com.jy.medical.adapter.viewholder.HospitalViewHolder;
import com.jy.medical.adapter.viewholder.SearchViewHolder;
import com.jy.medical.greendao.entities.HospitalData;
import com.jy.medical.greendao.entities.SearchData;
import com.jy.medical.greendao.manager.MaimDataManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.PublicString;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class HospitalAdapter extends BaseHeadFootAdapter {
    private Context context;
    private List<HospitalData> list;
    private String taskNo;
    private String flag;

    public HospitalAdapter(Context context, List<HospitalData> list, String taskNo, String flag) {
        this.context = context;
        this.list = list;
        this.taskNo = taskNo;
        this.flag = flag;
    }

    public void setData(List<HospitalData> list) {
        this.list = list;
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
        final HospitalViewHolder viewHolder = (HospitalViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"click",Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.text.setText(list.get(position).getHospitalName());
        viewHolder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag.equals("1")) {
                    Intent intent = new Intent(context, SelectDepartmentsActivity.class);
                    intent.putExtra("taskNo", taskNo);
                    intent.putExtra("hospitalId", list.get(position).getHospitalId());
                    intent.putExtra("hospitalName", list.get(position).getHospitalName());
                    ((AppCompatActivity) context).startActivityForResult(intent, PublicString.REQUEST_HOSPITAL);
                } else {
                    MaimDataManager maimDataManager = DaoUtils.getMaimDataInstance();
                    Intent intent = new Intent(context, SelectDepartmentsActivity.class);
                    intent.putExtra("taskNo", taskNo);
                    intent.putExtra("hospitalId", list.get(position).getHospitalId());
                    intent.putExtra("hospitalName", list.get(position).getHospitalName());
                    maimDataManager.updateDepartment(taskNo, list.get(position).getHospitalId(), list.get(position).getHospitalName());
//                    ((AppCompatActivity) context).setResult(Activity.RESULT_OK, intent);
                    ((AppCompatActivity) context).finish();
                    MedicalApplication.getInstance().finishActivity(SelectHospitalActivity.class);

                }
            }
        });
    }

    @Override
    public HospitalViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hospital, null);
        return new HospitalViewHolder(view);
    }
}
