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
import com.jy.medical.greendao.entities.ToolData;

import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class ToolAdapter extends BaseHeadFootAdapter {

    private Context context;
    private List<ToolData>list;

    public ToolAdapter(Context context, List<ToolData> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    protected void onBindHeaderView(View headerView) {
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"head was clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onBindFooterView(View footerView) {
//        footerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context,"foot was clicked",Toast.LENGTH_SHORT).show();
//            }
//        });
    }



    @Override
    protected int getItemNum() {
        return list.size();
    }



    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, final int position) {
        final ToolViewHolder viewHolder= (ToolViewHolder) holder;
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                view.setBackgroundColor(Color.parseColor("#F5F5F5"));
                viewHolder.toolText.setTextColor(Color.parseColor("#999999"));
//                Toast.makeText(context,"big text "+position+"was clicked",Toast.LENGTH_SHORT).show();
//                view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                context.startActivity(new Intent(context, LawDetailActivity.class));
            }
        });

        viewHolder.toolTab.setText(list.get(position).getToolTab());
        if (list.get(position).getToolTab().length()==2){
            viewHolder.toolText.setText("         "+list.get(position).getToolText());
        }else {
            viewHolder.toolText.setText("            "+list.get(position).getToolText());
        }
        viewHolder.toolKind.setText(list.get(position).getToolKind());
        viewHolder.toolTime.setText(list.get(position).getToolTime());
    }

    @Override
    public ToolViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_tool,null);
        return new ToolViewHolder(view);
    }
}
