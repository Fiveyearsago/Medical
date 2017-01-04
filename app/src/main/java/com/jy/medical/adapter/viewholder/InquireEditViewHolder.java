package com.jy.medical.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jy.medical.R;

/**
 * Created by 19459 on 2016/9/21.
 */

public class InquireEditViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView phone;
    public TextView peopleId;

    public InquireEditViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.item_contact_edit_name);
        phone = (TextView) itemView.findViewById(R.id.item_contact_edit_phone);
        peopleId = (TextView) itemView.findViewById(R.id.item_contact_edit_id);
    }
}
