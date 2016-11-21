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

public class NursingViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView nursingId;
    public CleanableEditText name;
    public CleanableEditText days;
    public CleanableEditText fee;
    public ImageButton deleteImageButton;

    public NursingViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.item_nursing_title);
        nursingId = (TextView) itemView.findViewById(R.id.item_nursing_id);
        name = (CleanableEditText) itemView.findViewById(R.id.item_nursing_name);
        days = (CleanableEditText) itemView.findViewById(R.id.item_nursing_days);
        fee = (CleanableEditText) itemView.findViewById(R.id.item_nursing_fee);
        deleteImageButton= (ImageButton) itemView.findViewById(R.id.item_delete_image);
    }
}
