package com.jy.medical.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jy.medical.R;

/**
 * Created by 19459 on 2016/9/21.
 */

public class AddressViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView text;
    public AddressViewHolder(View itemView) {
        super(itemView);
//        itemView.setBackground();
        title = (TextView) itemView.findViewById(R.id.item_address_title);
        text = (TextView) itemView.findViewById(R.id.item_address_text);
    }
}
