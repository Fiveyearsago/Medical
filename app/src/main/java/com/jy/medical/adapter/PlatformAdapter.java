package com.jy.medical.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
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

    public void setData(List<PlatformData> list) {
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
        PlatformViewHolder viewHolder = (PlatformViewHolder) holder;
        final PlatformData platformData = list.get(position);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, FollowDetailActivity.class);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("info", platformData);
                ((AppCompatActivity) context).startActivityForResult(intent, 0x11);

            }
        });


        int gapNum = TimeUtil.getGapCount(platformData.getTime());
        Log.i("gapNum", gapNum + "天后超时" + platformData.getTime());
        if (gapNum < 0 && !platformData.getCommitFlag().equals("1")) {
            if (platformData.getIsAllFlag().equals("1")) {
                viewHolder.viewLayout.setVisibility(View.GONE);
                viewHolder.platformTag.setText("已超时");
                viewHolder.platformTag.setTextColor(context.getResources().getColor(R.color.colorTimeout));
                viewHolder.platformTag.setBackground(context.getResources().getDrawable(R.drawable.platform_timeout));

            } else {
                String string = "超时" + (-gapNum) + "天";
                Log.i("gapNum", "超时" + gapNum + "天");
                SpannableStringBuilder style = new SpannableStringBuilder(string);
                style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorTimeout)), 2, 2 + (-gapNum + "").length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(42);
                style.setSpan(absoluteSizeSpan, 2, 2 + (-gapNum + "").length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//            viewHolder.platformTimeOutNum.setText(gapNum + "");
                viewHolder.platformTimeOutNum.setText(style);
                viewHolder.viewLayout.setVisibility(View.VISIBLE);
                if (platformData.getIsDoingFlag() != null && platformData.getIsDoingFlag().equals("1")) {
                    viewHolder.platformTag.setText("进行中");
                    viewHolder.platformTag.setTextColor(context.getResources().getColor(R.color.white));
                    viewHolder.platformTag.setBackground(context.getResources().getDrawable(R.drawable.platform_doing));
                } else {
                    viewHolder.platformTag.setText("待办");
                    viewHolder.platformTag.setTextColor(context.getResources().getColor(R.color.white));
                    viewHolder.platformTag.setBackground(context.getResources().getDrawable(R.drawable.platform_undo));
                }
            }
        } else if (gapNum >= 0 && !platformData.getCommitFlag().equals("1")) {
            if (platformData.getIsAllFlag().equals("1")) {
                viewHolder.viewLayout.setVisibility(View.GONE);
                viewHolder.platformTag.setText("未超时");
                viewHolder.platformTag.setTextColor(context.getResources().getColor(R.color.colorYellow));
                viewHolder.platformTag.setBackground(context.getResources().getDrawable(R.drawable.platform_untimeout));

            } else {
                gapNum++;
                String string = gapNum + "天后超时";
                SpannableStringBuilder style = new SpannableStringBuilder(string);
                style.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorYellow)), 0, (gapNum + "").length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(42);
                style.setSpan(absoluteSizeSpan, 0, (gapNum + "").length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                viewHolder.platformTimeOutNum.setText(style);
                viewHolder.viewLayout.setVisibility(View.VISIBLE);

                if (platformData.getIsDoingFlag() != null && platformData.getIsDoingFlag().equals("1")) {
                    viewHolder.platformTag.setText("进行中");
                    viewHolder.platformTag.setTextColor(context.getResources().getColor(R.color.white));
                    viewHolder.platformTag.setBackground(context.getResources().getDrawable(R.drawable.platform_doing));
                } else {
                    viewHolder.platformTag.setText("待办");
                    viewHolder.platformTag.setTextColor(context.getResources().getColor(R.color.white));
                    viewHolder.platformTag.setBackground(context.getResources().getDrawable(R.drawable.platform_undo));
                }
            }

        } else if (platformData.getCommitFlag().equals("1")) {
            viewHolder.platformTag.setText("已完成");
            viewHolder.viewLayout.setVisibility(View.GONE);
            viewHolder.platformTag.setTextColor(context.getResources().getColor(R.color.colorFinished));
            viewHolder.platformTag.setBackground(context.getResources().getDrawable(R.drawable.platform_finished));

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
