package com.jy.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.activities.LawDetailActivity;
import com.jy.medical.adapter.viewholder.LawViewHolder;
import com.jy.medical.greendao.entities.LawData;
import com.jy.medical.greendao.entities.ToolData;

import java.util.List;

/**
 * Created by songran on 16/10/10.
 */

public class LawAdapter extends BaseHeadFootAdapter {
    private Context context;
    private List<LawData> list;

    public LawAdapter(Context context, List<LawData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    protected void onBindHeaderView(View headerView) {

    }

    @Override
    protected void onBindFooterView(View footerView) {

    }

    @Override
    protected int getItemNum() {
        return list.size();
    }

    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, int position) {
        final LawViewHolder viewHolder= (LawViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                view.setBackgroundColor(Color.parseColor("#F5F5F5"));
                viewHolder.lawText.setTextColor(Color.parseColor("#999999"));
                context.startActivity(new Intent(context, LawDetailActivity.class));
            }
        });
        viewHolder.lawText.setText(list.get(position).getLawFullName());
        viewHolder.lawOrganization.setText(list.get(position).getLawShortName());
        viewHolder.lawTime.setText(list.get(position).getCreateDate());
    }

    @Override
    protected LawViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_law,null);
        return new LawViewHolder(view);
    }
    public void setData(List<LawData> list){
        this.list=list;
    }
}
