package com.jy.medical.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jy.medical.R;

/**
 * Created by songran on 16/10/10.
 */

public class PictureHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public PictureHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.item_picture);
    }
}
