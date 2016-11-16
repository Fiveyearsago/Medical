package com.jy.medical.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jy.medical.R;

/**
 * Created by 19459 on 2016/9/21.
 */

public class CityViewHolder extends RecyclerView.ViewHolder {

    public TextView name;

    public CityViewHolder(View itemView) {
        super(itemView);
//        itemView.setBackground();
        name = (TextView) itemView.findViewById(R.id.item_city);
    }
}
