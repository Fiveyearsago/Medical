package com.jy.medical.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jy.medical.R;

/**
 * Created by 19459 on 2016/9/21.
 */

public class ToolViewHolder extends RecyclerView.ViewHolder {

    public TextView toolTab;
    public TextView toolText;
    public TextView toolKind;
    public TextView toolTime;

    public ToolViewHolder(View itemView) {
        super(itemView);
//        itemView.setBackground();
        toolTab = (TextView) itemView.findViewById(R.id.tool_tab);
        toolText = (TextView) itemView.findViewById(R.id.tool_text);
        toolKind = (TextView) itemView.findViewById(R.id.tool_kind_text);
        toolTime = (TextView) itemView.findViewById(R.id.tool_time);
    }
}
