package com.jy.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.activities.SelectDiagnoseActivity;
import com.jy.medical.adapter.viewholder.HumanPartsViewHolder;
import com.jy.medical.adapter.viewholder.SearchViewHolder;
import com.jy.medical.greendao.entities.HumanParts;
import com.jy.medical.greendao.entities.SearchData;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class HumanPartsAdapter extends BaseHeadFootAdapter {

    private Context context;
    private List<HumanParts> list;
    private String taskNo;

    public HumanPartsAdapter(Context context, List<HumanParts> list,String taskNo) {
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
        final HumanPartsViewHolder viewHolder = (HumanPartsViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SelectDiagnoseActivity.class);
                intent.putExtra("kindCode",list.get(position).getAid());
                intent.putExtra("name",list.get(position).getName());
                intent.putExtra("taskNo",taskNo);
                context.startActivity(intent);
            }
        });

        viewHolder.text.setText(list.get(position).getName());
    }

    @Override
    public HumanPartsViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_human_parts, null);
        return new HumanPartsViewHolder(view);
    }
}
