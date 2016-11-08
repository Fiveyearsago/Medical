package com.jy.medical.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.widget.CleanableEditText;

/**
 * Created by 19459 on 2016/9/21.
 */

public class ContactViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public CleanableEditText name;
    public CleanableEditText phone;
    public View deleteImage;
    public View addImage;
    public ImageButton addImageButton;
    public ImageButton deleteImageButton;

    public ContactViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.item_title);
        name = (CleanableEditText) itemView.findViewById(R.id.item_edit_name);
        phone = (CleanableEditText) itemView.findViewById(R.id.item_edit_phone);
        deleteImage =  itemView.findViewById(R.id.item_delete_layout);
        addImage = itemView.findViewById(R.id.item_contact_layout);
        addImageButton= (ImageButton) itemView.findViewById(R.id.item_add_image);
        deleteImageButton= (ImageButton) itemView.findViewById(R.id.item_delete_image);
    }
}
