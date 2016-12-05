package com.jy.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.jy.medical.activities.DetailActivity;
import com.jy.medical.activities.EarningActivity;
import com.jy.medical.activities.FollowDetailActivity;
import com.jy.medical.adapter.viewholder.PlatformViewHolder;
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

    public void setData(List<PlatformData> list){
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
        final PlatformData platformData = list.get(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, FollowDetailActivity.class);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("info", platformData);
                ((AppCompatActivity)context).startActivityForResult(intent,0x11);

            }
        });

        try {
            int gapNum = TimeUtil.getGapCount(platformData.getTime());
            if (TimeUtil.getGapCount(platformData.getTime()) > 0&&!platformData.getCommitFlag().equals("1")) {
                viewHolder.platformTimeOutNum.setText(gapNum + "");
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
        viewHolder.platformTime.setText(TimeUtil.getTimeString(platformData.getTime()));
        viewHolder.platformReportNum.setText(platformData.getReportNum());
        switch (platformData.getTaskType()) {
            case "01":
                viewHolder.taskType.setText("医");
                break;
            case "02":
                viewHolder.taskType.setText("薪");
                break;
            case "03":
                viewHolder.taskType.setText("误");
                break;
            case "04":
                viewHolder.taskType.setText("籍");
                break;
            case "05":
                viewHolder.taskType.setText("扶");
                break;
            case "06":
                viewHolder.taskType.setText("死");
                break;
            case "07":
                viewHolder.taskType.setText("医");
                break;
            case "08":
                viewHolder.taskType.setText("残");
                break;
            case "09":
                viewHolder.taskType.setText("基");
                break;
            case "10":
                viewHolder.taskType.setText("处");
                break;
        }

    }

    @Override
    public PlatformViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_platform, null);
        return new PlatformViewHolder(view);
    }
}
