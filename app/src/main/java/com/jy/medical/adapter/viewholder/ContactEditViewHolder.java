package com.jy.medical.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.widget.CleanableEditText;

/**
 * Created by 19459 on 2016/9/21.
 */

public class ContactEditViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView phone;
    public ImageButton deleteImage;

    public ContactEditViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.item_contact_edit_name);
        phone = (TextView) itemView.findViewById(R.id.item_contact_edit_phone);
        deleteImage =  (ImageButton) itemView.findViewById(R.id.item_contact_edit_image);
    }
}
