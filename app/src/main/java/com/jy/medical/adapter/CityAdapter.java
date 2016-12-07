package com.jy.medical.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.adapter.viewholder.CityViewHolder;
import com.jy.medical.greendao.entities.CityData;
import com.jy.medical.util.SPUtils;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class CityAdapter extends BaseHeadFootAdapter {

    private Context context;
    private List<CityData>list;

    public CityAdapter(Context context, List<CityData> list) {
        this.context = context;
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
        final CityViewHolder viewHolder= (CityViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SPUtils.put(context,"cityName",list.get(position).getName());
                SPUtils.put(context,"cityKey",list.get(position).getAid());
                Intent intent=new Intent();
                intent.putExtra("cityName",list.get(position).getName());
                intent.putExtra("cityKey",list.get(position).getAid());
                ((AppCompatActivity)context).setResult(Activity.RESULT_OK,intent);
                ((AppCompatActivity) context).finish();
            }
        });

        viewHolder.name.setText(list.get(position).getName());
    }

    @Override
    public CityViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_city,parent,false);
        return new CityViewHolder(view);
    }
}
