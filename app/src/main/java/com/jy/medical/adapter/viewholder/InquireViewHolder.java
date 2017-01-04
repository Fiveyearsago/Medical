package com.jy.medical.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.widget.ClearEditText;

/**
 * Created by 19459 on 2016/9/21.
 */

public class InquireViewHolder extends RecyclerView.ViewHolder {

    public TextView peopleId;
    public ClearEditText name;
    public ClearEditText phone;
    public View addImage;
    public ImageButton addImageButton;

    public InquireViewHolder(View itemView) {
        super(itemView);
        peopleId = (TextView) itemView.findViewById(R.id.item_people_id);
        name = (ClearEditText) itemView.findViewById(R.id.item_edit_name);
        phone = (ClearEditText) itemView.findViewById(R.id.item_edit_phone);
        addImage = itemView.findViewById(R.id.item_contact_layout);
        addImageButton = (ImageButton) itemView.findViewById(R.id.item_add_image);
    }
}
