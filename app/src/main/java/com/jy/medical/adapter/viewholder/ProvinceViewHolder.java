package com.jy.medical.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jy.medical.R;

/**
 * Created by SongRan on 2016/9/21.
 */

public class ProvinceViewHolder extends RecyclerView.ViewHolder {

    public TextView name;

    public ProvinceViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.item_province);
    }
}
