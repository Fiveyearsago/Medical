package com.jy.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jy.medical.R;
import com.jy.medical.activities.AddContactsActivity;
import com.jy.medical.activities.LocalAlbumActivity;
import com.jy.medical.activities.SelectDepartmentsActivity;
import com.jy.medical.adapter.viewholder.HospitalViewHolder;
import com.jy.medical.adapter.viewholder.SearchViewHolder;
import com.jy.medical.greendao.entities.HospitalData;
import com.jy.medical.greendao.entities.SearchData;
import com.jy.medical.util.PublicString;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class HospitalAdapter extends BaseHeadFootAdapter {
    private Context context;
    private List<HospitalData> list;
    private String taskNo;

    public HospitalAdapter(Context context, List<HospitalData> list,String taskNo) {
        this.context = context;
        this.list = list;
        this.taskNo=taskNo;
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
                Toast.makeText(context,"click",Toast.LENGTH_SHORT).show();
//                Bundle bundle=new Bundle();
//                bundle.putString("taskNo",taskNo);
                Intent intent = new Intent(context, SelectDepartmentsActivity.class);
                intent.putExtra("taskNo",taskNo);
                intent.putExtra("hospitalId",list.get(position).getHospitalId());
                intent.putExtra("hospitalName",list.get(position).getHospitalName());
                ((AppCompatActivity)context).startActivityForResult(intent, PublicString.REQUEST_HOSPITAL);
            }
        });
    }

    @Override
    public HospitalViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hospital, null);
        return new HospitalViewHolder(view);
    }
}
