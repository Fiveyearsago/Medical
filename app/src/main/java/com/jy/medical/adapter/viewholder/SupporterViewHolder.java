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

public class SupporterViewHolder extends RecyclerView.ViewHolder {
    public ClearEditText name;
    public TextView title;
    public TextView bornTime;
    public TextView houseKind;
    public TextView relation;
    public ClearEditText age;
    public ClearEditText years;
    public ClearEditText num;
    public ClearEditText address;
    public ImageButton deleteImageButton;
    public ImageButton location;

    public SupporterViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.item_supporter_title);
        bornTime = (TextView) itemView.findViewById(R.id.item_born_date);
        houseKind = (TextView) itemView.findViewById(R.id.item_households_nature);
        relation = (TextView) itemView.findViewById(R.id.item_relation);
        name = (ClearEditText) itemView.findViewById(R.id.item_supporter_name);
        age = (ClearEditText) itemView.findViewById(R.id.item_age);
        years = (ClearEditText) itemView.findViewById(R.id.item_years);
        num = (ClearEditText) itemView.findViewById(R.id.item_num);
        address = (ClearEditText) itemView.findViewById(R.id.item_address);
        deleteImageButton= (ImageButton) itemView.findViewById(R.id.item_delete_image);
        location= (ImageButton) itemView.findViewById(R.id.item_location);
    }
}
