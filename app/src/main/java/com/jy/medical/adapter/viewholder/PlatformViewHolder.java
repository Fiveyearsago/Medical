package com.jy.medical.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jy.medical.R;

/**
 * Created by 19459 on 2016/9/21.
 */

public class PlatformViewHolder extends RecyclerView.ViewHolder {

    public TextView platformTag;
    public TextView platformPeopleName;
    public TextView platformReportNum;
    public TextView platformTime;
    public View viewLayout;
    public TextView platformTimeOutNum;
    public TextView taskType;

    public PlatformViewHolder(View itemView) {
        super(itemView);
//        itemView.setBackground();
        platformPeopleName = (TextView) itemView.findViewById(R.id.item_platform_people_name);
        platformTag = (TextView) itemView.findViewById(R.id.item_platform_tag);
        platformTime = (TextView) itemView.findViewById(R.id.item_platform_time_text);
        platformReportNum = (TextView) itemView.findViewById(R.id.item_platform_report_num);
        platformTimeOutNum = (TextView) itemView.findViewById(R.id.item_time_out_num);
        taskType = (TextView) itemView.findViewById(R.id.item_task_type);
        viewLayout =  itemView.findViewById(R.id.time_out_layout);
    }
}
