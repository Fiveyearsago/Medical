package com.jy.medical.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jy.medical.R;

/**
 * Created by 19459 on 2016/9/21.
 */

public class DepartmentViewHolder extends RecyclerView.ViewHolder {

    public TextView text;
    public CheckBox checkBox;
    public DepartmentViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.item_department);
        checkBox= (CheckBox) itemView.findViewById(R.id.checkbox_department);
    }
}
