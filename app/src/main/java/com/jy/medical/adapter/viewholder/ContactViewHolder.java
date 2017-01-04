package com.jy.medical.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jy.medical.R;
import com.jy.medical.widget.CleanableEditText;
import com.jy.medical.widget.ClearEditText;

/**
 * Created by 19459 on 2016/9/21.
 */

public class ContactViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public ClearEditText name;
    public ClearEditText phone;
    public View deleteImage;
    public View addImage;
    public ImageButton addImageButton;
    public ImageButton deleteImageButton;

    public ContactViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.item_title);
        name = (ClearEditText) itemView.findViewById(R.id.item_edit_name);
        phone = (ClearEditText) itemView.findViewById(R.id.item_edit_phone);
        deleteImage =  itemView.findViewById(R.id.item_delete_layout);
        addImage = itemView.findViewById(R.id.item_contact_layout);
        addImageButton= (ImageButton) itemView.findViewById(R.id.item_add_image);
        deleteImageButton= (ImageButton) itemView.findViewById(R.id.item_delete_image);
    }
}
