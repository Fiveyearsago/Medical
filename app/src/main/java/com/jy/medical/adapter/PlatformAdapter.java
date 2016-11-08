package com.jy.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jy.medical.R;
import com.jy.medical.activities.FollowDetailActivity;
import com.jy.medical.greendao.entities.PlatformData;
import com.jy.medical.util.TimeUtil;

import java.text.ParseException;
import java.util.List;

/**
 * Created by 19459 on 2016/9/21.
 */

public class PlatformAdapter extends BaseHeadFootAdapter {

    private Context context;
    private List<PlatformData> list;

    public PlatformAdapter(Context context, List<PlatformData> list) {
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
    }


    @Override
    protected int getItemNum() {
        return list.size();
    }


    @Override
    protected void onBindView(RecyclerView.ViewHolder holder, final int position) {
        final PlatformViewHolder viewHolder = (PlatformViewHolder) holder;
        final PlatformData platformData=list.get(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, FollowDetailActivity.class);
                intent.putExtra("info",platformData);
                context.startActivity(intent);

            }
        });

        try {
            int gapNum = TimeUtil.getGapCount(platformData.getTime());
            if (TimeUtil.getGapCount(platformData.getTime()) > 0) {
                viewHolder.platformTimeOutNum.setText(gapNum+"");
                viewHolder.viewLayout.setVisibility(View.VISIBLE);
                viewHolder.platformTag.setText("超时");
                viewHolder.platformTag.setTextColor(context.getResources().getColor(R.color.colorTimeout));
                viewHolder.platformTag.setBackground(context.getResources().getDrawable(R.drawable.platform_timeout));

            } else {
                viewHolder.platformTag.setText("完成");
                viewHolder.viewLayout.setVisibility(View.GONE);
                viewHolder.platformTag.setTextColor(context.getResources().getColor(R.color.colorFinished));
                viewHolder.platformTag.setBackground(context.getResources().getDrawable(R.drawable.platform_finished));

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (platformData.getTag()) {
            case "0":

                break;
            case "1":
                break;
            case "2":
                break;
        }
        viewHolder.platformPeopleName.setText(platformData.getPeopleName());
        viewHolder.platformTime.setText(platformData.getTime());
        viewHolder.platformReportNum.setText(platformData.getReportNum());
    }

    @Override
    public PlatformViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_platform, null);
        return new PlatformViewHolder(view);
    }
}
